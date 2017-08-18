package ru.beetlewar.strategy.gameplay.matches.gameplay.buildings;

import ru.beetlewar.strategy.gameplay.matches.gameplay.players.PlayerNumber;

public class BuildingId {
    private final int number;
    private final PlayerNumber playerNumber;

    private BuildingId(int number, PlayerNumber playerNumber) {
        this.number = number;
        this.playerNumber = playerNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingId that = (BuildingId) o;

        if (number != that.number) return false;
        return playerNumber != null ? playerNumber.equals(that.playerNumber) : that.playerNumber == null;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (playerNumber != null ? playerNumber.hashCode() : 0);
        return result;
    }

    public static BuildingId initial(PlayerNumber playerNumber) {
        return new BuildingId(1, playerNumber);
    }

    public BuildingId next() {
        return new BuildingId(number + 1, playerNumber);
    }
}
