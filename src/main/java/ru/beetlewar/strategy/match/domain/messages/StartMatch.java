package ru.beetlewar.strategy.match.domain.messages;

public class StartMatch {
    public final int frameRate;

    public StartMatch(int frameRate){
        this.frameRate = frameRate;
    }
}
