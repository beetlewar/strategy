package ru.beetlewar.strategy.gameplay.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import ru.beetlewar.strategy.contracts.events.MatchComplete;
import ru.beetlewar.strategy.gameplay.messages.*;
import scala.concurrent.duration.FiniteDuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Match extends AbstractActor {
    static public Props props(ActorRef eventStoreRef) {
        return Props.create(Match.class, () -> new Match(eventStoreRef));
    }

    private long periodMilliseconds;
    private final ArrayList<PlayerNumber> players = new ArrayList<>();
    private PlayerNumber winPlayer;
    private final ActorRef eventStoreRef;

    private Match(ActorRef eventStoreRef) {
        this.eventStoreRef = eventStoreRef;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreateMap.class, cm -> createMap(cm))
                .match(CreatePlayer.class, cp -> createPlayer(cp))
                .match(StartMatch.class, sm -> start(sm))
                .match(MatchTick.class, mt -> tick())
                .match(PlayerDestroyed.class, pd -> destroyPlayer(pd))
                .build();
    }

    private void createMap(CreateMap cm) {
        ActorRef actorRef = getContext().actorOf(Map.props(cm.size), "map");

        sender().tell(actorRef, self());
    }

    private void createPlayer(CreatePlayer cp) {
        ActorRef actorRef = getContext().actorOf(Player.props(cp), "player." + cp.number);

        players.add(cp.number);

        sender().tell(actorRef, self());
    }

    private void start(StartMatch sm) {
        periodMilliseconds = 1000 / sm.frameRate;

        context().system().scheduler().schedule(
                FiniteDuration.Zero(),
                FiniteDuration.create(periodMilliseconds, TimeUnit.MILLISECONDS),
                self(),
                new MatchTick(),
                context().dispatcher(),
                ActorRef.noSender());

        sender().tell(new Object(), self());
    }

    private void tick() {
    }

    private void destroyPlayer(PlayerDestroyed pd) {
        players.remove(pd.number);

        if (players.size() == 1) {
            winPlayer = players.get(0);

            eventStoreRef.tell(new MatchComplete(), self());
        }
    }
}
