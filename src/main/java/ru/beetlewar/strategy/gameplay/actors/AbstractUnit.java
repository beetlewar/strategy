package ru.beetlewar.strategy.gameplay.actors;

import akka.actor.AbstractActor;
import ru.beetlewar.strategy.gameplay.geometry.Coordinate;

public abstract class AbstractUnit extends AbstractActor {
    private final UnitId unitId;
    private Coordinate coordinate;

    public AbstractUnit(UnitId unitId, Coordinate coordinate){
        this.unitId = unitId;
        this.coordinate = coordinate;
    }
}
