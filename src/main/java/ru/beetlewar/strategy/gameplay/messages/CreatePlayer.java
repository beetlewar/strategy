package ru.beetlewar.strategy.gameplay.messages;

import ru.beetlewar.strategy.gameplay.assets.Resource2;
import ru.beetlewar.strategy.gameplay.assets.Resource1;
import ru.beetlewar.strategy.gameplay.actors.PlayerNumber;

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
