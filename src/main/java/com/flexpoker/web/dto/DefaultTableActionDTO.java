package com.flexpoker.web.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultTableActionDTO {

    private final UUID gameId;

    private final UUID tableId;

    @JsonCreator
    public DefaultTableActionDTO(@JsonProperty(value = "gameId") UUID gameId,
            @JsonProperty(value = "tableId") UUID tableId) {
        this.gameId = gameId;
        this.tableId = tableId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getTableId() {
        return tableId;
    }

}