package ru.beetlewar.strategy.match.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.beetlewar.strategy.match.domain.IMatchEventsConsumer;
import ru.beetlewar.strategy.match.domain.IMatchEventsRepository;

@Component
public class MatchService implements IMatchService {
    @Autowired
    private IMatchEventsRepository matchEventsRepository;

    @Override
    public String getString() {
        return "Hello from real service";
    }

    @Override
    public void subscribeMatchEvents(IMatchEventsConsumer eventsConsumer){
        matchEventsRepository.subscribeMatchEventsConsumer(eventsConsumer);
    }
}
