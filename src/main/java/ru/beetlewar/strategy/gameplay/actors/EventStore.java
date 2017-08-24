package ru.beetlewar.strategy.gameplay.actors;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.beetlewar.strategy.gameplay.port.IEventsRepository;

import java.util.ArrayList;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EventStore extends UntypedAbstractActor {
    public static Props props() {
        return Props.create(EventStore.class, () -> new EventStore());
    }

    @Autowired
    private IEventsRepository eventsRepository;

    private EventStore() {
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        ArrayList<Object> events = new ArrayList<>();
        events.add(o);

        eventsRepository.saveEvents(events);
    }
}
