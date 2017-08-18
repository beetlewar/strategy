package ru.beetlewar.strategy.gameplay.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class EventStore extends AbstractActor {
    public static Props props() {
        return Props.create(EventStore.class, () -> new EventStore());
    }

    private EventStore(){
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(e -> handleEvent(e))
                .build();
    }

    private void handleEvent(Object evt) {
        int i = 3;
    }
}
