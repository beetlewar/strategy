package ru.beetlewar.strategy.match.application;

import ru.beetlewar.strategy.match.domain.IMatchEventsConsumer;

public interface IMatchService {
    String getString();

    void subscribeMatchEvents(IMatchEventsConsumer eventsConsumer);
}
