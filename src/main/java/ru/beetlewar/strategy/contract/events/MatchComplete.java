package ru.beetlewar.strategy.contract.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.beetlewar.strategy.contract.MatchId;

public class MatchComplete extends MatchEventBase {
    @JsonCreator
    public MatchComplete(@JsonProperty("matchId") MatchId matchId) {
        super(matchId);
    }
}