package ru.beetlewar.strategy.gameplay.matches.gameplay.units;

import ru.beetlewar.strategy.gameplay.matches.gameplay.Resource1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.Resource2;
import ru.beetlewar.strategy.gameplay.matches.gameplay.geometry.Coordinate;

public class CreateWorker {
    public final static Resource1 RESOURCE_1 = new Resource1(5);
    public final static Resource2 RESOURCE_2 = new Resource2(0);

    public final Resource1 resource1;
    public final Resource2 resource2;
    public final Coordinate coordinate;

    public CreateWorker(Resource1 resource1, Resource2 resource2, Coordinate coordinate) {
        this.resource1 = resource1;
        this.resource2 = resource2;
        this.coordinate = coordinate;
    }

    public static CreateWorker create(Coordinate coordinate) {
        return new CreateWorker(RESOURCE_1, RESOURCE_2, coordinate);
    }
}
