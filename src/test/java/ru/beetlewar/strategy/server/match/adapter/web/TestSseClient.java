package ru.beetlewar.strategy.server.match.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.junit.Assert;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.*;

public class TestSseClient {
    private final URI uri;
    private final ConcurrentLinkedQueue events;
    private final ArrayList expectedEvents;

    public TestSseClient(URI uri){
        this.uri = uri;
        events = new ConcurrentLinkedQueue();
        expectedEvents = new ArrayList();
    }

    public void expectEvent(Object evt){
        expectedEvents.add(evt);
    }

    public Future receive(){
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

                events.add(evt);
            }

            return new Object();
        });

        ExecutorService scheduler = Executors.newCachedThreadPool();
        scheduler.submit(task);

        return task;
    }

    public void waitEventsToBeReceived()throws Exception {
        boolean allEventsReceived = true;

        for(int i = 0; i < 5; i++) {
            allEventsReceived = true;

            for (Object evt : expectedEvents) {
                allEventsReceived = eventReceived(evt);

                if(!allEventsReceived){
                    break;
                }
            }

            if(allEventsReceived){
                break;
            }

            Thread.sleep(1000);
        }

        Assert.assertTrue(allEventsReceived);
    }

    private boolean eventReceived(Object evt) {
        for (Object receivedEvent : events) {
            if (evt.getClass().equals(receivedEvent.getClass())) {
                return true;
            }
        }

        return false;
    }
}
