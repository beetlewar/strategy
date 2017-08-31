package ru.beetlewar.strategy.server.match.adapter.persistence;

import org.springframework.stereotype.Component;
import ru.beetlewar.strategy.server.match.domain.IMatchEventsConsumer;
import ru.beetlewar.strategy.server.match.domain.IMatchEventsRepository;
import ru.beetlewar.strategy.server.match.domain.IMatchEventsConsumer;
import ru.beetlewar.strategy.server.match.domain.IMatchEventsRepository;

import java.util.ArrayList;

@Component
public class MatchEventsRepository implements IMatchEventsRepository {
    private final ArrayList<Object> events = new ArrayList<>();
    private final ArrayList<IMatchEventsConsumer> eventConsumers = new ArrayList<>();

    @Override
    public Iterable<Object> all() {
        return events;
    }

    @Override
    public void saveEvents(Iterable<Object> events) {
        for (Object evt : events) {
            this.events.add(evt);
        }

        for (Object evt : events) {
            fireEvent(evt);
        }
    }

    private void fireEvent(Object evt) {
        for (IMatchEventsConsumer consumer : eventConsumers) {
            fireEventFor(consumer, evt);
        }
    }

    private void fireEventFor(IMatchEventsConsumer matchEventsConsumer, Object evt) {
        matchEventsConsumer.consume(evt);
    }

    @Override
    public void subscribeMatchEventsConsumer(IMatchEventsConsumer matchEventsConsumer) {
        eventConsumers.add(matchEventsConsumer);

        fireEventsFor(matchEventsConsumer);
    }

    private void fireEventsFor(IMatchEventsConsumer matchEventsConsumer) {
        for (Object evt : events) {
            fireEventFor(matchEventsConsumer, evt);
        }
    }
}
