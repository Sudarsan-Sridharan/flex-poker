package com.flexpoker.table.command.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flexpoker.framework.event.BaseEvent;
import com.flexpoker.model.card.Card;
import com.flexpoker.table.command.framework.TableEvent;

public class CardsShuffledEvent extends BaseEvent implements TableEvent {

    private final UUID gameId;

    private final List<Card> shuffledDeck;

    @JsonCreator
    public CardsShuffledEvent(@JsonProperty(value = "aggregateId") UUID aggregateId,
            @JsonProperty(value = "version") int version,
            @JsonProperty(value = "gameId") UUID gameId,
            @JsonProperty(value = "shuffledDeck") List<Card> shuffledDeck) {
        super(aggregateId, version);
        this.gameId = gameId;
        this.shuffledDeck = new ArrayList<>(shuffledDeck);
    }

    @Override
    public UUID getGameId() {
        return gameId;
    }

    public List<Card> getShuffledDeck() {
        return new ArrayList<>(shuffledDeck);
    }

}
