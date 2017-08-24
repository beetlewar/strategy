package ru.beetlewar.strategy.gameplay.actors;

import org.springframework.stereotype.Component;

@Component
public class SomeInjection {
    public void PrintString(String str) {
        System.out.println(str);
    }
}
