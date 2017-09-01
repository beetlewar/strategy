package ru.beetlewar.strategy.client;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TestMatchEventsHandler implements IMatchEventsHandler {
    private final ConcurrentLinkedQueue actualEvents = new ConcurrentLinkedQueue();
    private final ArrayList expectedEvents = new ArrayList();

    @Override
    public void handle(Object evt) {
        actualEvents.add(evt);
    }

    public void expectEvent(Object evt) {
        expectedEvents.add(evt);
    }

    public void waitEventsTobeHandled() throws Exception {
        boolean allEventsHandled = true;

        for(int i = 0; i < 5; i++) {
            allEventsHandled = true;

            for (Object evt : expectedEvents) {
                allEventsHandled = eventHandled(evt);

                if(!allEventsHandled){
                    break;
                }
            }

            if(allEventsHandled){
                break;
            }

            Thread.sleep(1000);
        }

        Assert.assertTrue(allEventsHandled);
    }

    private boolean eventHandled(Object evt) {
        for (Object handledEvt : actualEvents) {
            if (evt.getClass().equals(handledEvt.getClass())) {
                return true;
            }
        }

        return false;
    }
}
