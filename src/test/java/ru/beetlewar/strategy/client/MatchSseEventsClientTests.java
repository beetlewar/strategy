package ru.beetlewar.strategy.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.beetlewar.strategy.contract.MatchId;
import ru.beetlewar.strategy.contract.events.MatchComplete;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MatchSseEventsClientTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestMatchSseController testController;

    @Test
    public void should_receive_all_events_via_sse() throws Exception {
        TestMatchEventsHandler testHandler = new TestMatchEventsHandler();

        Object[] events = new Object[]{new MatchComplete(new MatchId(1))};

        for (Object evt : events) {
            testController.expectEvent(evt);
            testHandler.expectEvent(evt);
        }

        URI uri = new URI(String.format("http://localhost:%s", port));

        MatchSseEventsClient sut = new MatchSseEventsClient(new MatchId(1), uri, testHandler);

        sut.subscribeForMatchEvents();

        testHandler.waitEventsTobeHandled();
    }
}
