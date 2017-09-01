package ru.beetlewar.strategy.server.match.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.beetlewar.strategy.contract.MatchId;
import ru.beetlewar.strategy.server.match.domain.IMatchEventsConsumer;
import ru.beetlewar.strategy.server.match.domain.IMatchEventsRepository;

@Component
public class MatchService implements IMatchService {
    @Autowired
    private IMatchEventsRepository matchEventsRepository;

    @Override
    public String getString() {
        return "Hello from real service";
    }

    @Override
    public void subscribeMatchEvents(MatchId matchId, IMatchEventsConsumer eventsConsumer){
        matchEventsRepository.subscribeMatchEventsConsumer(matchId, eventsConsumer);
    }
}
