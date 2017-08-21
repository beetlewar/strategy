package ru.beetlewar.strategy.gameplay.matches.gameplay.matches;

import akka.actor.AbstractActor;
import akka.actor.Props;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.Player;

public class Match extends AbstractActor {
    static public Props props() {
        return Props.create(Match.class, () -> new Match());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreatePlayer.class, createPlayer -> {
                    getContext().actorOf(Player.props(createPlayer));
                })
                .build();
    }
}
