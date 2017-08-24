package ru.beetlewar.strategy.gameplay.port;

public interface IEventsRepository {
    Iterable<Object> all();

    void saveEvents(Iterable<Object> events);

    void addListener(IEventRepositoryListener listener);

    void removeListener(IEventRepositoryListener listener);
}
