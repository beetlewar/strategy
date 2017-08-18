package ru.beetlewar.strategy.gameplay.matches.gameplay.buildings;

import akka.actor.Props;
import ru.beetlewar.strategy.gameplay.matches.gameplay.geometry.Rectangle;

public class Building1 extends AbstractBuilding {
    static public Props props(BuildingId id, Rectangle rect) {
        return Props.create(Building1.class, () -> new Building1(id, rect));
    }

    private Building1(BuildingId id, Rectangle rect) {
        super(id, rect);
    }
}
