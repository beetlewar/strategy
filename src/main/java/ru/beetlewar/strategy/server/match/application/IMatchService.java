package ru.beetlewar.strategy.server.match.application;

import ru.beetlewar.strategy.contract.MatchId;
import ru.beetlewar.strategy.server.match.domain.IMatchEventsConsumer;

public interface IMatchService {
    String getString();

    void subscribeMatchEvents(MatchId matchId, IMatchEventsConsumer eventsConsumer);
}
