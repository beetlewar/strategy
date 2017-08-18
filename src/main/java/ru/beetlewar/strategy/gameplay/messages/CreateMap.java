package ru.beetlewar.strategy.gameplay.messages;

import ru.beetlewar.strategy.gameplay.geometry.Size;

public class CreateMap {
    public final Size size;

    public CreateMap(Size size) {
        this.size = size;
    }
}
