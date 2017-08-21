package ru.beetlewar.strategy.gameplay.matches.gameplay.players;

import akka.actor.AbstractActor;
import akka.actor.Props;
import ru.beetlewar.strategy.gameplay.matches.gameplay.buildings.Building1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.matches.CreatePlayer;

public class Player extends AbstractActor {
    static public Props props(CreatePlayer createPlayer) {
        return Props.create(Player.class, () -> new Player(createPlayer.number, createPlayer.resource1, createPlayer.resource2));
    }

    private final PlayerNumber number;
    private Resource1 resource1;
    private Resource2 resource2;

    public Player(PlayerNumber number, Resource1 resource1, Resource2 resource2) {
        this.number = number;
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreateBuilding1.class, cb ->{
                    resource1 = resource1.substruct(cb.resource1);
                    resource2 = resource2.substruct(cb.resource2);

                    getContext().actorOf(Building1.props(cb));
                })
                .build();
    }
}
