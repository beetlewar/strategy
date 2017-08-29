package ru.beetlewar.strategy.match.adapter.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.beetlewar.strategy.match.adapter.consumers.SseEmitterMatchEventsConsumer;
import ru.beetlewar.strategy.match.application.IMatchService;

@RestController
public class MatchEventsController {
    private final IMatchService matchService;

    public MatchEventsController(IMatchService matchService){
        this.matchService = matchService;
    }

    @RequestMapping(path = "/api/events", method = RequestMethod.GET)
    public SseEmitter subscribeMatchEvents() {
        SseEmitter emitter = new SseEmitter();

        SseEmitterMatchEventsConsumer consumer = new SseEmitterMatchEventsConsumer(emitter);

        matchService.subscribeMatchEvents(consumer);

        return emitter;
    }
}
