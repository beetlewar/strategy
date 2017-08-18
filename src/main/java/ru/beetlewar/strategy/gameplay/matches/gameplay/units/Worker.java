package ru.beetlewar.strategy.gameplay.matches.gameplay.units;

import akka.actor.Props;
import ru.beetlewar.strategy.gameplay.matches.gameplay.geometry.Coordinate;

public class Worker extends AbstractUnit {
    public static Props props(UnitId id, Coordinate coordinate) {
        return Props.create(Worker.class, () -> new Worker(id, coordinate));
    }

    private Worker(UnitId unitId, Coordinate coordinate) {
        super(unitId, coordinate);
    }

    @Override
    public Receive createReceive() {
        return null;
    }
}
