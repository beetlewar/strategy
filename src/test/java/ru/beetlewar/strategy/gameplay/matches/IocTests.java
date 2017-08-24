package ru.beetlewar.strategy.gameplay.matches;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IocTests {
    @Test
    public void ioc_test(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("ru.beetlewar.strategy");

        SomeService s = ctx.getBean(SomeService.class);

        ctx.close();
    }
}

