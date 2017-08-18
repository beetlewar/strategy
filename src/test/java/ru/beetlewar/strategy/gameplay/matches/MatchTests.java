package ru.beetlewar.strategy.gameplay.matches;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.junit.Assert;
import org.junit.Test;
import ru.beetlewar.strategy.gameplay.actors.EventStore;
import ru.beetlewar.strategy.gameplay.actors.Match;
import ru.beetlewar.strategy.gameplay.actors.PlayerNumber;
import ru.beetlewar.strategy.gameplay.assets.Resource1;
import ru.beetlewar.strategy.gameplay.assets.Resource2;
import ru.beetlewar.strategy.gameplay.geometry.*;
import ru.beetlewar.strategy.gameplay.messages.*;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class MatchTests {
    @Test
    public void test() throws Exception {
        ActorSystem system = ActorSystem.create("test");

        ActorRef eventsRef = system.actorOf(EventStore.props(), "events");

        ActorRef matchRef = system.actorOf(Match.props(eventsRef), "match");

        initMap(new Size(new Width(100), new Height(100)), matchRef);

        initPlayer(new PlayerNumber(1), matchRef);

        ActorRef player2Ref = initPlayer(new PlayerNumber(2), matchRef);

        startMatch(matchRef);

        player2Ref.tell(new DestroyPlayer(), ActorRef.noSender());

        waitMatchComplete(matchRef);
    }

    private static void initMap(Size size, ActorRef matchRef) throws Exception {
        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
        Duration dur = Duration.create(5, TimeUnit.SECONDS);

        Await.result(Patterns.ask(matchRef, new CreateMap(size), timeout), dur);
    }

    private static ActorRef initPlayer(
            PlayerNumber playerNumber,
            ActorRef matchRef) throws Exception {
        CreatePlayer createPlayer = new CreatePlayer(playerNumber, new Resource1(50), new Resource2(0));

        Duration dur = Duration.create(5, TimeUnit.SECONDS);

        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

        return (ActorRef) Await.result(Patterns.ask(matchRef, createPlayer, timeout), dur);
    }

    private static void startMatch(ActorRef matchActor) throws Exception {
        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
        Duration dur = Duration.create(5, TimeUnit.SECONDS);

        Await.result(Patterns.ask(matchActor, new StartMatch(60), timeout), dur);
    }

    private static void waitMatchComplete(ActorRef matchActor) throws Exception {
        Duration dur = Duration.create(5, TimeUnit.SECONDS);

        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

        for(int i = 0; i < 10; i++) {
//            Object matchStatus = Await.result(Patterns.ask(matchActor, new RequestMatchStatus(), timeout), dur);
//
//            if (matchStatus instanceof MatchComplete) {
//                return;
//            }

            Thread.sleep(1000);
        }

        Assert.fail();
    }
}