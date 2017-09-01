package ru.beetlewar.strategy.server.match.domain.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import ru.beetlewar.strategy.contract.MatchId;
import ru.beetlewar.strategy.contract.events.MatchComplete;
import ru.beetlewar.strategy.server.match.domain.messages.CreatePlayer;
import ru.beetlewar.strategy.server.match.domain.messages.MatchTick;
import ru.beetlewar.strategy.server.match.domain.messages.PlayerDestroyed;
import ru.beetlewar.strategy.server.match.domain.messages.StartMatch;
import scala.concurrent.duration.FiniteDuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Match extends AbstractActor {
    static public Props props(MatchId matchId, ActorRef eventStoreRef) {
        return Props.create(Match.class, () -> new Match(matchId, eventStoreRef));
    }

    private long periodMilliseconds;
    private final ArrayList<PlayerNumber> players = new ArrayList<>();
    private PlayerNumber winPlayer;
    private MatchId matchId;
    private final ActorRef eventStoreRef;

    private Match(MatchId matchId, ActorRef eventStoreRef) {
        this.matchId = matchId;
        this.eventStoreRef = eventStoreRef;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreatePlayer.class, cp -> createPlayer(cp))
                .match(StartMatch.class, sm -> start(sm))
                .match(MatchTick.class, mt -> tick())
                .match(PlayerDestroyed.class, pd -> destroyPlayer(pd))
                .build();
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

            eventStoreRef.tell(new MatchComplete(matchId), self());
        }
    }
}
