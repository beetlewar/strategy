package ru.beetlewar.strategy.match.domain.actors;

public class PlayerNumber {
    public final int number;

    public PlayerNumber(int number){
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerNumber that = (PlayerNumber) o;

        return number == that.number;
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public String toString() {
        return "p" + Integer.toString(number);
    }
}
