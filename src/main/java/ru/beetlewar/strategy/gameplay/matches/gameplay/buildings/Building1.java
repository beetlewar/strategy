package ru.beetlewar.strategy.gameplay.matches.gameplay.buildings;

import akka.actor.Props;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.CreateBuilding1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.Resource1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.Resource2;

public class Building1 extends AbstractBuilding {
    public final static Resource1 RESOURCE_1 = new Resource1(10);
    public final static Resource2 RESOURCE_2 = new Resource2(0);

    static public Props props(CreateBuilding1 createBuilding1) {
        return Props.create(Building1.class, () -> new Building1(createBuilding1.resource1, createBuilding1.resource2));
    }

    private final Resource1 resource1;
    private final Resource2 resource2;

    public Building1(Resource1 resource1, Resource2 resource2) {
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
