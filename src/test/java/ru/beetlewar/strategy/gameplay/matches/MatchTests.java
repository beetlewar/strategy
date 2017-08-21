package ru.beetlewar.strategy.gameplay.matches;

import akka.actor.*;
import akka.dispatch.sysmsg.Create;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.junit.Test;
import ru.beetlewar.strategy.gameplay.matches.gameplay.matches.CreatePlayer;
import ru.beetlewar.strategy.gameplay.matches.gameplay.matches.Match;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.PlayerNumber;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.Resource1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.Resource2;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

public class MatchTests {
    @Test
    public void test() throws Exception {
        ActorSystem system = ActorSystem.create();

        CreatePlayer createPlayer = new CreatePlayer(new PlayerNumber(1), new Resource1(10), new Resource2(0));

        ActorRef myActor = system.actorOf(MyActor.props());

        Future<Object> f = Patterns.ask(myActor, createPlayer, 5000);

        String awaitResult = (String)Await.result(f, Duration.create(5, TimeUnit.SECONDS));

//        final ActorRef matchActor = system.actorOf(Match.props(), "match");
//
//        CreatePlayer createPlayer = new CreatePlayer(new PlayerNumber(1), new Resource1(10), new Resource2(0));
//
//        Future<Object> f = Patterns.ask(matchActor, createPlayer, new Timeout(5, TimeUnit.SECONDS));
//
//        Object result = Await.result(f, Duration.apply("10 sec"));

        //matchActor.tell(, ActorRef.noSender());
    }
}



