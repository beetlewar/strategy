package ru.beetlewar.strategy.gameplay.matches.gameplay.matches;

import ru.beetlewar.strategy.gameplay.matches.gameplay.players.PlayerNumber;

public class MatchComplete {
    public final PlayerNumber winPlayer;

    public MatchComplete(PlayerNumber winPlayer){
        this.winPlayer = winPlayer;
    }
}
