package ru.beetlewar.strategy.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import ru.beetlewar.strategy.contract.MatchId;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MatchSseEventsClient {
    private MatchId matchId;
    private final URI uri;
    private final IMatchEventsHandler matchEventsListener;

    public MatchSseEventsClient(MatchId matchId, URI baseAddress, IMatchEventsHandler matchEventsListener) {
        this.matchId = matchId;
        this.uri = baseAddress.resolve("/api/matches/").resolve(matchId.toString());
        this.matchEventsListener = matchEventsListener;
    }

    public Future subscribeForMatchEvents() {
        FutureTask task = new FutureTask<>(() -> {
            Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();

            WebTarget target = client.target(uri);

            EventInput input = target.request().get(EventInput.class);

            while (!input.isClosed()) {
                InboundEvent inboundEvent = input.read();
                if (inboundEvent == null) {
                    break;
                }

                Class<?> eventClass = Class.forName(inboundEvent.getName());

                ObjectMapper mapper = new ObjectMapper();
                Object evt = mapper.readValue(inboundEvent.readData(), eventClass);

                notifyEvent(evt);
            }

            return new Object();
        });

        ExecutorService scheduler = Executors.newCachedThreadPool();
        scheduler.submit(task);

        return task;
    }

    private void notifyEvent(Object evt) {
        IMatchEventsHandler listener = matchEventsListener;
        if (listener != null) {
            listener.handle(evt);
        }
    }
}
