package ru.beetlewar.strategy.server.match.domain.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import ru.beetlewar.strategy.server.match.assets.Resource1;
import ru.beetlewar.strategy.server.match.assets.Resource2;
import ru.beetlewar.strategy.server.match.domain.messages.CreatePlayer;
import ru.beetlewar.strategy.server.match.domain.messages.DestroyPlayer;
import ru.beetlewar.strategy.server.match.domain.messages.PlayerDestroyed;
import ru.beetlewar.strategy.server.match.assets.Resource1;
import ru.beetlewar.strategy.server.match.assets.Resource2;
import ru.beetlewar.strategy.server.match.domain.messages.CreatePlayer;
import ru.beetlewar.strategy.server.match.domain.messages.PlayerDestroyed;

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
