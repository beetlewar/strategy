package ru.beetlewar.strategy.gameplay.matches;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import ru.beetlewar.strategy.gameplay.matches.gameplay.matches.CreatePlayer;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.Resource1;

public class MyActor extends AbstractActor {
    public static Props props() {
        return Props.create(MyActor.class, () -> new MyActor());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreatePlayer.class, cp -> {
                    Resource1 r1 = cp.resource1.add(cp.resource1);

                    sender().tell("hello!", self());
                })
                .matchAny(a -> {
                    new Resource1(10);
                })
                .build();
    }
}
