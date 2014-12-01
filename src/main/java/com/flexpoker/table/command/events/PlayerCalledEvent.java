package com.flexpoker.table.command.events;

import java.util.UUID;

import com.flexpoker.framework.event.BaseEvent;
import com.flexpoker.table.command.framework.TableEvent;
import com.flexpoker.table.command.framework.TableEventType;

public class PlayerCalledEvent extends BaseEvent<TableEventType> implements TableEvent {

    private static final TableEventType TYPE = TableEventType.PlayerCalled;

    private final UUID gameId;

    private final UUID playerId;

    public PlayerCalledEvent(UUID aggregateId, int version, UUID gameId, UUID playerId) {
        super(aggregateId, version, TYPE);
        this.gameId = gameId;
        this.playerId = playerId;
    }

    @Override
    public UUID getGameId() {
        return gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

}
