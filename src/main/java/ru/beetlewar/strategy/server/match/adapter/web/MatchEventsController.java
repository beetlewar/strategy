package ru.beetlewar.strategy.server.match.adapter.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.beetlewar.strategy.contract.HttpPaths;
import ru.beetlewar.strategy.contract.MatchId;
import ru.beetlewar.strategy.server.match.adapter.consumers.SseEmitterMatchEventsConsumer;
import ru.beetlewar.strategy.server.match.application.IMatchService;

@RestController
public class MatchEventsController {
    private final IMatchService matchService;

    public MatchEventsController(IMatchService matchService) {
        this.matchService = matchService;
    }

    @RequestMapping(path = HttpPaths.MatchEvents, method = RequestMethod.GET)
    public SseEmitter subscribeMatchEvents(@PathVariable long matchId) {
        SseEmitter emitter = new SseEmitter();

        SseEmitterMatchEventsConsumer consumer = new SseEmitterMatchEventsConsumer(emitter);

        matchService.subscribeMatchEvents(new MatchId(matchId), consumer);

        return emitter;
    }
}
