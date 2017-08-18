package ru.beetlewar.strategy.gameplay.matches;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.junit.Assert;
import org.junit.Test;
import ru.beetlewar.strategy.gameplay.matches.gameplay.Resource1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.Resource2;
import ru.beetlewar.strategy.gameplay.matches.gameplay.buildings.CreateBuilding1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.buildings.DestroyBuilding;
import ru.beetlewar.strategy.gameplay.matches.gameplay.geometry.*;
import ru.beetlewar.strategy.gameplay.matches.gameplay.matches.*;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.CreatePlayer;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.PlayerNumber;
import ru.beetlewar.strategy.gameplay.matches.gameplay.units.CreateWorker;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class MatchTests {
    @Test
    public void test() throws Exception {
        ActorSystem system = ActorSystem.create();

        ActorRef matchActor = system.actorOf(Match.props());

        initMap(new Size(new Width(100), new Height(100)), matchActor);

        initPlayer(1, new Position(new Row(0), new Col(0)), system, matchActor);

        String playerBuildPath = initPlayer(2, new Position(new Row(90), new Col(90)), system, matchActor);

        matchActor.tell(new StartMatch(1), ActorRef.noSender());

        ActorRef builderActorRef = system.provider().resolveActorRef(playerBuildPath);

        builderActorRef.tell(new DestroyBuilding(), ActorRef.noSender());

        waitMatchComplete(matchActor);
    }

    private static void initMap(Size size, ActorRef matchActor) throws Exception {
        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
        Duration dur = Duration.create(5, TimeUnit.SECONDS);

        Await.result(Patterns.ask(matchActor, new CreateMap(size), timeout), dur);
    }

    private static String initPlayer(
            int number,
            Position building1Pos,
            ActorSystem system,
            ActorRef matchActor) throws Exception {
        CreatePlayer createPlayer = new CreatePlayer(new PlayerNumber(number), new Resource1(50), new Resource2(0));

        Duration dur = Duration.create(5, TimeUnit.SECONDS);

        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

        String playerPath = (String) Await.result(Patterns.ask(matchActor, createPlayer, timeout), dur);

        ActorRef player = system.provider().resolveActorRef(playerPath);

        String buildingPath = (String) Await.result(Patterns.ask(player, CreateBuilding1.create(building1Pos), timeout), dur);

        for (int i = 0; i < 4; i++) {
            Await.result(Patterns.ask(player, CreateWorker.create(new Coordinate(0, 0, 0)), timeout), dur);
        }

        return buildingPath;
    }

    private static void waitMatchComplete(ActorRef matchActor) throws Exception {
        Duration dur = Duration.create(5, TimeUnit.SECONDS);

        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

        for(int i = 0; i < 10; i++) {
            Object matchStatus = Await.result(Patterns.ask(matchActor, new RequestMatchStatus(), timeout), dur);

            if (matchStatus instanceof MatchComplete) {
                return;
            }

            Thread.sleep(1000);
        }

        Assert.fail();
    }
}