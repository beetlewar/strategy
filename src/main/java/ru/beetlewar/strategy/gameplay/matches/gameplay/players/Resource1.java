package ru.beetlewar.strategy.gameplay.matches.gameplay.players;

public class Resource1 {
    private final int value;

    public Resource1(int value) {
        this.value = value;
    }

    public Resource1 add(Resource1 value) {
        return new Resource1(this.value + value.value);
    }

    public Resource1 substruct(Resource1 value) {
        return new Resource1(this.value - value.value);
    }
}
