package ru.beetlewar.strategy.match.domain;

public interface IMatchEventsRepository {
    Iterable<Object> all();

    void saveEvents(Iterable<Object> events);

    void subscribeMatchEventsConsumer(IMatchEventsConsumer matchEventsConsumer);
}
