package ru.beetlewar.strategy.server.match.domain.messages;

public class StartMatch {
    public final int frameRate;

    public StartMatch(int frameRate){
        this.frameRate = frameRate;
    }
}
