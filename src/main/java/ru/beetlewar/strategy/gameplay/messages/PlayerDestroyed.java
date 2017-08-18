package ru.beetlewar.strategy.gameplay.messages;

import ru.beetlewar.strategy.gameplay.actors.PlayerNumber;

public class PlayerDestroyed {
    public final PlayerNumber number;

    public PlayerDestroyed(PlayerNumber number){
        this.number = number;
    }
}
