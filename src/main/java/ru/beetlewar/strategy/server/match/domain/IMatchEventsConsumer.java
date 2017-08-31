package ru.beetlewar.strategy.server.match.domain;

public interface IMatchEventsConsumer {
    void consume(Object evt);
}
