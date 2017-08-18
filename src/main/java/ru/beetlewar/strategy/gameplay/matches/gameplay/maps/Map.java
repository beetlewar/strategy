package ru.beetlewar.strategy.gameplay.matches.gameplay.maps;

import akka.actor.AbstractActor;
import akka.actor.Props;
import ru.beetlewar.strategy.gameplay.matches.gameplay.geometry.Size;

public class Map extends AbstractActor {
    public static Props props(Size size) {
        return Props.create(Map.class, () -> new Map(size));
    }

    private final Size size;

    public Map(Size size) {
        this.size = size;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
