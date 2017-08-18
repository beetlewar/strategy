package ru.beetlewar.strategy.gameplay.matches.gameplay.buildings;

import ru.beetlewar.strategy.gameplay.matches.gameplay.Resource1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.Resource2;
import ru.beetlewar.strategy.gameplay.matches.gameplay.geometry.*;

public class CreateBuilding1 {
    public final static Resource1 RESOURCE_1 = new Resource1(10);
    public final static Resource2 RESOURCE_2 = new Resource2(0);
    public final static Size SIZE = new Size(new Width(10), new Height(10));

    public final Resource1 resource1;
    public final Resource2 resource2;
    public final Rectangle rect;

    public CreateBuilding1(Resource1 resource1, Resource2 resource2, Rectangle rect) {
        this.resource1 = resource1;
        this.resource2 = resource2;
        this.rect = rect;
    }

    public static CreateBuilding1 create(Position pos) {

        return new CreateBuilding1(RESOURCE_1, RESOURCE_2, new Rectangle(pos, SIZE));
    }
}
