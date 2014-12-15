package com.flexpoker.table.command.events;

import java.util.UUID;

import com.flexpoker.framework.event.BaseEvent;
import com.flexpoker.table.command.framework.TableEvent;
import com.flexpoker.table.command.framework.TableEventType;

public class TurnCardDealtEvent extends BaseEvent<TableEventType> implements TableEvent {

    private static final TableEventType TYPE = TableEventType.TurnCardDealt;

    private final UUID gameId;

    private final UUID handId;

    public TurnCardDealtEvent(UUID aggregateId, int version, UUID gameId, UUID handId) {
        super(aggregateId, version, TYPE);
        this.gameId = gameId;
        this.handId = handId;
    }

    @Override
    public UUID getGameId() {
        return gameId;
    }

    public UUID getHandId() {
        return handId;
    }

}