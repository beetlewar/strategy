package ru.beetlewar.strategy.gameplay.matches.gameplay.units;

import ru.beetlewar.strategy.gameplay.matches.gameplay.players.PlayerNumber;

public class UnitId {
    private final int number;
    private final PlayerNumber playerNumber;

    private UnitId(int number, PlayerNumber playerNumber) {
        this.number = number;
        this.playerNumber = playerNumber;
    }

    public static UnitId initial(PlayerNumber playerNumber) {
        return new UnitId(0, playerNumber);
    }

    public UnitId next() {
        return new UnitId(number + 1, playerNumber);
    }
}
