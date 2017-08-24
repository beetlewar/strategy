package ru.beetlewar.strategy.gameplay.actors;

import akka.actor.UntypedAbstractActor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActorWithInjection extends UntypedAbstractActor {
    private SomeInjection injection;

    public ActorWithInjection(SomeInjection injection){
        this.injection = injection;
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if(o instanceof Message){
            injection.PrintString(((Message)o).message);
        }
    }

    public static class Message{
        public final String message;

        public Message(String message){
            this.message = message;
        }
    }
}
