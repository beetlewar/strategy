package ru.beetlewar.strategy.server.match.domain.messages;

import ru.beetlewar.strategy.server.match.assets.Resource2;
import ru.beetlewar.strategy.server.match.assets.Resource1;
import ru.beetlewar.strategy.server.match.domain.actors.PlayerNumber;

public class CreatePlayer {
    public final PlayerNumber number;
    public final Resource1 resource1;
    public final Resource2 resource2;

    public CreatePlayer(
            PlayerNumber number,
            Resource1 resource1,
            Resource2 resource2){
        this.number = number;
        this.resource1 = resource1;
        this.resource2 = resource2;
    }
}
