package ru.beetlewar.strategy.server.match.domain;

public interface IMatchEventsRepository {
    Iterable<Object> all();

    void saveEvents(Iterable<Object> events);

    void subscribeMatchEventsConsumer(IMatchEventsConsumer matchEventsConsumer);
}
