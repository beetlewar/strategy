package ru.beetlewar.strategy.server.match.adapter.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.beetlewar.strategy.contract.MatchId;
import ru.beetlewar.strategy.contract.events.MatchComplete;
import ru.beetlewar.strategy.server.match.domain.IMatchEventsRepository;

import java.net.URI;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpTests {
    @LocalServerPort
    private int port;

    @Autowired
    private IMatchEventsRepository matchEventsRepository;

    @Test
    public void should_send_events_from_repository() throws Exception {
        ArrayList expectedEvents = new ArrayList() {{
            add(new MatchComplete(new MatchId(1)));
        }};

        matchEventsRepository.saveEvents(expectedEvents);

        TestSseClient sseClient = new TestSseClient(new URI(String.format("http://localhost:%s/api/matches/1", port)));

        for(Object evt : expectedEvents) {
            sseClient.expectEvent(evt);
        }

        sseClient.receive();

        sseClient.waitEventsToBeReceived();
    }
}