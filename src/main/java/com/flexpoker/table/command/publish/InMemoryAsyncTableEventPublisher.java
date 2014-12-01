package com.flexpoker.table.command.publish;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.flexpoker.framework.event.Event;
import com.flexpoker.framework.event.EventHandler;
import com.flexpoker.framework.event.EventPublisher;
import com.flexpoker.table.command.events.HandDealtEvent;
import com.flexpoker.table.command.events.PlayerCalledEvent;
import com.flexpoker.table.command.events.PlayerCheckedEvent;
import com.flexpoker.table.command.events.PlayerFoldedEvent;
import com.flexpoker.table.command.events.PlayerRaisedEvent;
import com.flexpoker.table.command.events.TableCreatedEvent;
import com.flexpoker.table.command.framework.TableEventType;

@Component
public class InMemoryAsyncTableEventPublisher implements EventPublisher<TableEventType> {

    private final EventHandler<TableCreatedEvent> tableCreatedEventHandler;

    private final EventHandler<HandDealtEvent> handDealtEventHandler;

    private final EventHandler<PlayerCalledEvent> playerCalledEventHandler;

    private final EventHandler<PlayerCheckedEvent> playerCheckedEventHandler;

    private final EventHandler<PlayerFoldedEvent> playerFoldedEventHandler;

    private final EventHandler<PlayerRaisedEvent> playerRaisedEventHandler;

    @Inject
    public InMemoryAsyncTableEventPublisher(
            EventHandler<TableCreatedEvent> tableCreatedEventHandler,
            EventHandler<HandDealtEvent> handDealtEventHandler,
            EventHandler<PlayerCalledEvent> playerCalledEventHandler,
            EventHandler<PlayerCheckedEvent> playerCheckedEventHandler,
            EventHandler<PlayerFoldedEvent> playerFoldedEventHandler,
            EventHandler<PlayerRaisedEvent> playerRaisedEventHandler) {
        this.tableCreatedEventHandler = tableCreatedEventHandler;
        this.handDealtEventHandler = handDealtEventHandler;
        this.playerCalledEventHandler = playerCalledEventHandler;
        this.playerCheckedEventHandler = playerCheckedEventHandler;
        this.playerFoldedEventHandler = playerFoldedEventHandler;
        this.playerRaisedEventHandler = playerRaisedEventHandler;
    }

    @Override
    public void publish(Event<TableEventType> event) {
        switch (event.getType()) {
        case TableCreated:
            tableCreatedEventHandler.handle((TableCreatedEvent) event);
            break;
        case CardsShuffled:
            break;
        case HandDealtEvent:
            handDealtEventHandler.handle((HandDealtEvent) event);
            break;
        case PlayerCalled:
            playerCalledEventHandler.handle((PlayerCalledEvent) event);
            break;
        case PlayerChecked:
            playerCheckedEventHandler.handle((PlayerCheckedEvent) event);
            break;
        case PlayerFolded:
            playerFoldedEventHandler.handle((PlayerFoldedEvent) event);
            break;
        case PlayerRaised:
            playerRaisedEventHandler.handle((PlayerRaisedEvent) event);
            break;
        default:
            throw new IllegalArgumentException("Event Type cannot be handled: "
                    + event.getType());
        }
    }
}
