package ru.beetlewar.strategy.infrastructure.http;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@RestController
public class MatchController {
    @RequestMapping(path = "/api/some", method = RequestMethod.GET)
    public String getString() {
        return "hello, world!";
    }

    @RequestMapping(path = "/api/events", method = RequestMethod.GET)
    public SseEmitter subscribeMatchEvents() {
        SseEmitter emitter = new SseEmitter();

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    emitter.send("hello from emitter", MediaType.APPLICATION_JSON);
                } catch (IOException e) {
                }
            }
        };

        timer.schedule(task, 1000, 1000);

        return emitter;
    }
}
