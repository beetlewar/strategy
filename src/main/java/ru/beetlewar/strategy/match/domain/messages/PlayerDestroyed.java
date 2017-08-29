package ru.beetlewar.strategy.match.domain.messages;

import ru.beetlewar.strategy.match.domain.actors.PlayerNumber;

public class PlayerDestroyed {
    public final PlayerNumber number;

    public PlayerDestroyed(PlayerNumber number){
        this.number = number;
    }
}
