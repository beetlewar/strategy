package ru.beetlewar.strategy.server.match.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.beetlewar.strategy.server.match.contracts.events.MatchComplete;
import ru.beetlewar.strategy.server.match.domain.IMatchEventsRepository;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpTests {
    @LocalServerPort
    private int port;

    @Autowired
    private IMatchEventsRepository matchEventsRepository;

    @Test
    public void some_test() throws Exception {
        matchEventsRepository.saveEvents(new ArrayList<Object>() {{
            add(new MatchComplete());
        }});

        Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();

        WebTarget target = client.target("http://localhost:" + port + "/api/events");

        EventInput input = target.request().get(EventInput.class);

        while (!input.isClosed()) {
            InboundEvent inboundEvent = input.read();
            if (inboundEvent == null) {
                break;
            }

            Class<?> eventClass = Class.forName(inboundEvent.getName());

            if (eventClass == MatchComplete.class) {
                ObjectMapper mapper = new ObjectMapper();
                MatchComplete mc = mapper.readValue(inboundEvent.readData(), MatchComplete.class);
                int i = 3;
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
    }
}