package ru.beetlewar.strategy.match.domain.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.beetlewar.strategy.infrastructure.AppConfiguration;
import ru.beetlewar.strategy.match.assets.Resource1;
import ru.beetlewar.strategy.match.assets.Resource2;
import ru.beetlewar.strategy.match.contracts.events.MatchComplete;
import ru.beetlewar.strategy.match.domain.IMatchEventsRepository;
import ru.beetlewar.strategy.match.domain.messages.CreatePlayer;
import ru.beetlewar.strategy.match.domain.messages.DestroyPlayer;
import ru.beetlewar.strategy.match.domain.messages.StartMatch;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static ru.beetlewar.strategy.infrastructure.SpringExtension.SPRING_EXTENSION_PROVIDER;

public class MatchTests {
    private final AnnotationConfigApplicationContext ctx;

    private final ActorSystem system;

    public MatchTests() {
        ctx = new AnnotationConfigApplicationContext("ru.beetlewar.strategy");

        AppConfiguration cfg = ctx.getBean(AppConfiguration.class);

        system = cfg.actorSystem();
    }

    @Test
    public void should_complete_match_when_player_destroyed() throws Exception {

        ActorRef eventsRef = system.actorOf(SPRING_EXTENSION_PROVIDER.get(system).props("eventStore"), "eventStore");

        ActorRef matchRef = system.actorOf(Match.props(eventsRef), "match");

        initPlayer(new PlayerNumber(1), matchRef);

        ActorRef player2Ref = initPlayer(new PlayerNumber(2), matchRef);

        startMatch(matchRef);

        player2Ref.tell(new DestroyPlayer(), ActorRef.noSender());

        Assert.assertTrue(waitMatchComplete());
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

    private boolean waitMatchComplete() throws Exception {
        for (int i = 0; i < 10; i++) {
            IMatchEventsRepository repo = ctx.getBean(IMatchEventsRepository.class);

            for (Object event : repo.all()) {
                if (event instanceof MatchComplete) {
                    return true;
                }
            }

            Thread.sleep(1000);
        }

        return false;
    }
}