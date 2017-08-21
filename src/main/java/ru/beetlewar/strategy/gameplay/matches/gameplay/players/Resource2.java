package ru.beetlewar.strategy.gameplay.matches.gameplay.players;

public class Resource2 {
    private final int value;

    public Resource2(int value){
        this.value = value;
    }

    public Resource2 add(Resource2 value) {
        return new Resource2(this.value + value.value);
    }

    public Resource2 substruct(Resource2 value) {
        return new Resource2(this.value - value.value);
    }
}
