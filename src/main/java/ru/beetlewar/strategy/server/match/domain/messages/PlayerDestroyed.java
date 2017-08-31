package ru.beetlewar.strategy.server.match.domain.messages;

import ru.beetlewar.strategy.server.match.domain.actors.PlayerNumber;

public class PlayerDestroyed {
    public final PlayerNumber number;

    public PlayerDestroyed(PlayerNumber number){
        this.number = number;
    }
}
