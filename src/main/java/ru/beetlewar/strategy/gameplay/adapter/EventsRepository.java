package ru.beetlewar.strategy.gameplay.adapter;

import org.springframework.stereotype.Component;
import ru.beetlewar.strategy.gameplay.port.IEventRepositoryListener;
import ru.beetlewar.strategy.gameplay.port.IEventsRepository;

import java.util.ArrayList;

@Component
public class EventsRepository implements IEventsRepository {
    private final ArrayList<Object> events = new ArrayList<>();
    private final ArrayList<IEventRepositoryListener> eventListeners = new ArrayList<>();

    @Override
    public Iterable<Object> all() {
        return events;
    }

    @Override
    public void saveEvents(Iterable<Object> events) {
        for (Object evt : events) {
            this.events.add(evt);

            fireEvent(evt);
        }
    }

    private void fireEvent(Object evt){
        for(IEventRepositoryListener listener : eventListeners){
            listener.handle(evt);
        }
    }

    @Override
    public void addListener(IEventRepositoryListener listener) {
        eventListeners.add(listener);
    }

    @Override
    public void removeListener(IEventRepositoryListener listener) {
        eventListeners.remove(listener);
    }
}
