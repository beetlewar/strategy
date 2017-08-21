package ru.beetlewar.strategy.gameplay.matches.gameplay.matches;

import ru.beetlewar.strategy.gameplay.matches.gameplay.players.PlayerNumber;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.Resource1;
import ru.beetlewar.strategy.gameplay.matches.gameplay.players.Resource2;

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
