package ru.beetlewar.strategy.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;

@RestController
public class TestMatchSseController {
    private final ArrayList expectedEvents = new ArrayList();

    public void expectEvent(Object evt) {
        expectedEvents.add(evt);
    }

    @RequestMapping(path = "/api/matches/{matchId}", method = RequestMethod.GET)
    public SseEmitter subscribeEvents(@PathVariable long matchId) throws Exception {
        SseEmitter emitter = new SseEmitter();

        for (Object evt : expectedEvents) {
            sendEvents(emitter, evt);
        }

        return emitter;
    }

    private void sendEvents(SseEmitter emitter, Object evt) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String evtJson = objectMapper.writeValueAsString(evt);

        SseEmitter.SseEventBuilder builder = SseEmitter
                .event()
                .data(evtJson, MediaType.APPLICATION_JSON)
                .name(evt.getClass().getName());

        emitter.send(builder);
    }
}
