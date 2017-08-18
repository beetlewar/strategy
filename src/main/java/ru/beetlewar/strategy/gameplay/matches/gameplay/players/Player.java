package ru.beetlewar.strategy.gameplay.matches.gameplay.players;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import ru.beetlewar.strategy.gameplay.matches.gameplay.Resource1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.Resource2;
import ru.beetlewar.strategy.gameplay.matches.gameplay.buildings.Building1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.buildings.BuildingId;
import ru.beetlewar.strategy.gameplay.matches.gameplay.buildings.CreateBuilding1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.matches.PlayerDestroyed;
import ru.beetlewar.strategy.gameplay.matches.gameplay.units.CreateWorker;
import ru.beetlewar.strategy.gameplay.matches.gameplay.units.UnitId;
import ru.beetlewar.strategy.gameplay.matches.gameplay.units.Worker;

import java.util.ArrayList;

public class Player extends AbstractActor {
    static public Props props(CreatePlayer createPlayer) {
        return Props.create(Player.class, () -> new Player(createPlayer.number, createPlayer.resource1, createPlayer.resource2));
    }

    private final PlayerNumber number;
    private Resource1 resource1;
    private Resource2 resource2;

    private final ArrayList<BuildingId> buildings;
    private BuildingId lastBuildingId;

    private final ArrayList<UnitId> units;
    private UnitId lastUnitId;

    public Player(PlayerNumber number, Resource1 resource1, Resource2 resource2) {
        this.number = number;
        this.resource1 = resource1;
        this.resource2 = resource2;

        this.buildings = new ArrayList<BuildingId>();
        this.lastBuildingId = BuildingId.initial(number);

        this.units = new ArrayList<UnitId>();
        this.lastUnitId = UnitId.initial(number);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreateBuilding1.class, cb -> createBuilding1(cb))
                .match(CreateWorker.class, cw -> createWorker1(cw))
                .match(BuildingDestroyed.class, db -> destroyBuilding(db))
                .build();
    }

    private void createBuilding1(CreateBuilding1 cb) {
        resource1 = resource1.substruct(cb.resource1);
        resource2 = resource2.substruct(cb.resource2);

        BuildingId newBuildingId = lastBuildingId;

        ActorRef buildingActorRef = getContext().actorOf(Building1.props(newBuildingId, cb.rect));

        buildings.add(newBuildingId);

        lastBuildingId = lastBuildingId.next();

        sender().tell(buildingActorRef.path().toSerializationFormat(), self());
    }

    private void createWorker1(CreateWorker cw) {
        resource1 = resource1.substruct(cw.resource1);
        resource2 = resource2.substruct(cw.resource2);

        UnitId newUnitId = lastUnitId;

        getContext().actorOf(Worker.props(newUnitId, cw.coordinate));

        units.add(newUnitId);

        lastUnitId = lastUnitId.next();

        sender().tell(newUnitId, self());
    }

    private void destroyBuilding(BuildingDestroyed bd) {
        buildings.remove(bd.id);

        if (buildings.size() == 0) {
            getContext().parent().tell(new PlayerDestroyed(number), self());
        }
    }
}
