package com.flexpoker.table.command.handlers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.flexpoker.framework.command.CommandHandler;
import com.flexpoker.framework.event.EventPublisher;
import com.flexpoker.model.HandRanking;
import com.flexpoker.model.card.Card;
import com.flexpoker.model.card.CardsUsedInHand;
import com.flexpoker.model.card.PocketCards;
import com.flexpoker.table.command.aggregate.HandEvaluation;
import com.flexpoker.table.command.aggregate.Table;
import com.flexpoker.table.command.commands.StartNewHandForNewGameCommand;
import com.flexpoker.table.command.factory.TableFactory;
import com.flexpoker.table.command.framework.TableEvent;
import com.flexpoker.table.command.repository.TableEventRepository;
import com.flexpoker.table.command.service.CardService;
import com.flexpoker.table.command.service.HandEvaluatorService;

@Component
public class StartNewHandForNewGameCommandHandler implements
        CommandHandler<StartNewHandForNewGameCommand> {

    private final TableFactory tableFactory;

    private final EventPublisher<TableEvent> eventPublisher;

    private final TableEventRepository tableEventRepository;

    private final CardService cardService;

    private final HandEvaluatorService handEvaluatorService;

    @Inject
    public StartNewHandForNewGameCommandHandler(TableFactory tableFactory,
            EventPublisher<TableEvent> eventPublisher,
            TableEventRepository tableEventRepository, CardService cardService,
            HandEvaluatorService handEvaluatorService) {
        this.tableFactory = tableFactory;
        this.eventPublisher = eventPublisher;
        this.tableEventRepository = tableEventRepository;
        this.cardService = cardService;
        this.handEvaluatorService = handEvaluatorService;
    }

    @Async
    @Override
    public void handle(StartNewHandForNewGameCommand command) {
        List<TableEvent> tableEvents = tableEventRepository
                .fetchAll(command.getTableId());
        Table table = tableFactory.createFrom(tableEvents);

        List<Card> shuffledDeckOfCards = cardService.createShuffledDeck();
        CardsUsedInHand cardsUsedInHand = cardService.createCardsUsedInHand(
                shuffledDeckOfCards, table.getNumberOfPlayersAtTable());

        List<HandRanking> possibleHandRankings = handEvaluatorService
                .determinePossibleHands(cardsUsedInHand.getFlopCards(),
                        cardsUsedInHand.getTurnCard(), cardsUsedInHand.getRiverCard());
        Map<PocketCards, HandEvaluation> handEvaluations = handEvaluatorService
                .determineHandEvaluation(cardsUsedInHand.getFlopCards(),
                        cardsUsedInHand.getTurnCard(), cardsUsedInHand.getRiverCard(),
                        cardsUsedInHand.getPocketCards(), possibleHandRankings);

        table.startNewHandForNewGame(command.getSmallBlind(), command.getBigBlind(), shuffledDeckOfCards,
                cardsUsedInHand, handEvaluations);
        table.fetchNewEvents().forEach(x -> tableEventRepository.save(x));
        table.fetchNewEvents().forEach(x -> eventPublisher.publish(x));
    }

}
