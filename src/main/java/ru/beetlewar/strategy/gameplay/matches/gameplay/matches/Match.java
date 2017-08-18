package ru.beetlewar.strategy.gameplay.matches.gameplay.matches;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import ru.beetlewar.strategy.gameplay.matches.gameplay.maps.Map;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.CreatePlayer;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.Player;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.PlayerNumber;
import scala.concurrent.duration.FiniteDuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Match extends AbstractActor {
    static public Props props() {
        return Props.create(Match.class, () -> new Match());
    }

    private long periodMilliseconds;
    private long ticks;
    private Date startTime;
    private Date lastTick;
    private final ArrayList<PlayerNumber> players = new ArrayList<PlayerNumber>();
    private State state = State.initial;
    private PlayerNumber winPlayer;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreateMap.class, cm -> createMap(cm))
                .match(CreatePlayer.class, cp -> createPlayer(cp))
                .match(StartMatch.class, sm -> start(sm))
                .match(MatchTick.class, mt -> tick())
                .match(PlayerDestroyed.class, pd -> destroyPlayer(pd))
                .match(RequestMatchStatus.class, ms -> requestMatchStatus(ms))
                .build();
    }

    private void createMap(CreateMap cm) {
        ActorRef actorRef = getContext().actorOf(Map.props(cm.size));

        sender().tell(actorRef.path().toSerializationFormat(), self());
    }

    private void createPlayer(CreatePlayer cp) {
        ActorRef actorRef = getContext().actorOf(Player.props(cp));

        players.add(cp.number);

        sender().tell(actorRef.path().toSerializationFormat(), self());
    }

    private void start(StartMatch sm) {
        periodMilliseconds = 1000 / sm.frameRate;

        startTime = new Date();

        state = State.started;

        context().system().scheduler().schedule(
                FiniteDuration.Zero(),
                FiniteDuration.create(periodMilliseconds, TimeUnit.MILLISECONDS),
                self(),
                new MatchTick(),
                context().dispatcher(),
                ActorRef.noSender());
    }

    private void tick() {
        Date prevTime = lastTick;

        ticks++;
        lastTick = new Date();

        long spentTime = prevTime == null ? 0 : lastTick.getTime() - prevTime.getTime();
        long overTime = spentTime - periodMilliseconds;
        long delay = periodMilliseconds - overTime;

        double totalElapsedSeconds = (lastTick.getTime() - startTime.getTime()) / 1000.0;
        double fps = ticks / totalElapsedSeconds;

        //System.out.println(delay + ", " + ticks + ", " + fps);
        System.out.println(spentTime);
    }

    private void destroyPlayer(PlayerDestroyed pd) {
        players.remove(pd.number);

        if (players.size() == 1) {
            state = State.complete;
            winPlayer = players.get(0);
        }
    }

    private void requestMatchStatus(RequestMatchStatus ms) {
        switch (state) {
            case initial:
                sender().tell(new MatchInitial(), self());
                break;
            case started:
                sender().tell(new MatchStarted(), self());
                break;
            case complete:
                sender().tell(new MatchComplete(winPlayer), self());
                break;
            default:
                break;
        }
    }

    private enum State {
        initial,
        started,
        complete
    }
}
