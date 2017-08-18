package ru.beetlewar.strategy.gameplay.matches.gameplay.buildings;

import akka.actor.AbstractActor;
import ru.beetlewar.strategy.gameplay.matches.gameplay.geometry.Rectangle;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.BuildingDestroyed;

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
                .match(DestroyBuilding.class, db -> destroyBuilding())
                .build();
    }

    private void destroyBuilding() {
        context().parent().tell(new BuildingDestroyed(id), self());
    }
}