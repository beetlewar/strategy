package ru.beetlewar.strategy.contract.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import ru.beetlewar.strategy.contract.MatchId;

public abstract class MatchEventBase {
    public final MatchId matchId;

    @JsonCreator
    public MatchEventBase(MatchId matchId){
        this.matchId = matchId;
    }
}
