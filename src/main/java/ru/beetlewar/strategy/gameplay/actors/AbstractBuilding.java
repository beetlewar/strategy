package ru.beetlewar.strategy.gameplay.actors;

import akka.actor.AbstractActor;
import ru.beetlewar.strategy.gameplay.geometry.Rectangle;

public abstract class AbstractBuilding extends AbstractActor {
    private final BuildingId id;
    private final Rectangle rect;

    public AbstractBuilding(BuildingId id, Rectangle rect) {
        this.id = id;
        this.rect = rect;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }
}