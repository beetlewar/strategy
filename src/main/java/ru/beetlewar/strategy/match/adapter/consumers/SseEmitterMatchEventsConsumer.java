package ru.beetlewar.strategy.match.adapter.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.beetlewar.strategy.match.domain.IMatchEventsConsumer;

import java.io.IOException;

public class SseEmitterMatchEventsConsumer implements IMatchEventsConsumer {
    private final SseEmitter emitter;

    public SseEmitterMatchEventsConsumer(SseEmitter emitter) {
        this.emitter = emitter;
    }

    @Override
    public void consume(Object evt) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String evtJson = objectMapper.writeValueAsString(evt);

            SseEmitter.SseEventBuilder builder = SseEmitter
                .event()
                .data(evtJson, MediaType.APPLICATION_JSON)
                .name(evt.getClass().getName());

            emitter.send(builder);
        }
        catch (IOException ex){
            //TODO: handle ex
        }
    }
}
