package ru.beetlewar.strategy.server.match.domain;

import ru.beetlewar.strategy.contract.MatchId;

public interface IMatchEventsRepository {
    Iterable<Object> all();

    void saveEvents(Iterable events);

    void subscribeMatchEventsConsumer(MatchId matchId, IMatchEventsConsumer matchEventsConsumer);
}
