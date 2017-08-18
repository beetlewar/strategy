package ru.beetlewar.strategy.gameplay.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import ru.beetlewar.strategy.gameplay.assets.Resource1;
import ru.beetlewar.strategy.gameplay.assets.Resource2;
import ru.beetlewar.strategy.gameplay.messages.*;

import java.util.ArrayList;

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
                .match(DestroyPlayer.class, dp -> destroyPlayer())
                .build();
    }

    private void destroyPlayer(){
        context().parent().tell(new PlayerDestroyed(number), self());
    }
}
