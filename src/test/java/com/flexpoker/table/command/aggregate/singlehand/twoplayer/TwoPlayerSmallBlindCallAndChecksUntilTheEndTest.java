package com.flexpoker.table.command.aggregate.singlehand.twoplayer;

import static com.flexpoker.test.util.CommonAssertions.verifyEventIdsAndVersionNumbers;
import static com.flexpoker.test.util.CommonAssertions.verifyNumberOfEventsAndEntireOrderByType;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.flexpoker.table.command.aggregate.Table;
import com.flexpoker.table.command.aggregate.testhelpers.TableTestUtils;
import com.flexpoker.table.command.events.ActionOnChangedEvent;
import com.flexpoker.table.command.events.CardsShuffledEvent;
import com.flexpoker.table.command.events.FlopCardsDealtEvent;
import com.flexpoker.table.command.events.HandCompletedEvent;
import com.flexpoker.table.command.events.HandDealtEvent;
import com.flexpoker.table.command.events.LastToActChangedEvent;
import com.flexpoker.table.command.events.PlayerCalledEvent;
import com.flexpoker.table.command.events.PlayerCheckedEvent;
import com.flexpoker.table.command.events.PotAmountIncreasedEvent;
import com.flexpoker.table.command.events.PotCreatedEvent;
import com.flexpoker.table.command.events.RiverCardDealtEvent;
import com.flexpoker.table.command.events.RoundCompletedEvent;
import com.flexpoker.table.command.events.TableCreatedEvent;
import com.flexpoker.table.command.events.TurnCardDealtEvent;
import com.flexpoker.table.command.events.WinnersDeterminedEvent;
import com.flexpoker.table.command.framework.TableEvent;

public class TwoPlayerSmallBlindCallAndChecksUntilTheEndTest {

    @Test
    public void test() {
        UUID tableId = UUID.randomUUID();

        Table table = TableTestUtils.createBasicTableAndStartHand(tableId, UUID.randomUUID(),
                UUID.randomUUID());

        // use the info in action on event to get the player id of the small
        // blind
        UUID smallBlindAndButtonPlayerId = ((ActionOnChangedEvent) table.fetchNewEvents()
                .get(4)).getPlayerId();
        table.call(smallBlindAndButtonPlayerId);

        UUID bigBlindPlayerId = ((ActionOnChangedEvent) table.fetchNewEvents().get(6))
                .getPlayerId();
        table.check(bigBlindPlayerId);

        // post-flop
        table.check(bigBlindPlayerId);
        table.check(smallBlindAndButtonPlayerId);

        // post-turn
        table.check(bigBlindPlayerId);
        table.check(smallBlindAndButtonPlayerId);

        // post-river
        table.check(bigBlindPlayerId);
        table.check(smallBlindAndButtonPlayerId);

        List<TableEvent> newEvents = table.fetchNewEvents();

        verifyNumberOfEventsAndEntireOrderByType(
                newEvents,
                TableCreatedEvent.class,
                CardsShuffledEvent.class,
                HandDealtEvent.class,
                PotCreatedEvent.class,
                ActionOnChangedEvent.class,
                // pre-flop
                PlayerCalledEvent.class, ActionOnChangedEvent.class,
                PlayerCheckedEvent.class,
                PotAmountIncreasedEvent.class,
                RoundCompletedEvent.class, ActionOnChangedEvent.class,
                LastToActChangedEvent.class, FlopCardsDealtEvent.class,
                // post-flop
                PlayerCheckedEvent.class, ActionOnChangedEvent.class,
                PlayerCheckedEvent.class, RoundCompletedEvent.class,
                ActionOnChangedEvent.class, LastToActChangedEvent.class,
                TurnCardDealtEvent.class,
                // post-turn
                PlayerCheckedEvent.class, ActionOnChangedEvent.class,
                PlayerCheckedEvent.class, RoundCompletedEvent.class,
                ActionOnChangedEvent.class, LastToActChangedEvent.class,
                RiverCardDealtEvent.class,
                // post-river
                PlayerCheckedEvent.class, ActionOnChangedEvent.class,
                PlayerCheckedEvent.class, RoundCompletedEvent.class,
                WinnersDeterminedEvent.class, HandCompletedEvent.class);
        verifyEventIdsAndVersionNumbers(tableId, newEvents);
    }

}
