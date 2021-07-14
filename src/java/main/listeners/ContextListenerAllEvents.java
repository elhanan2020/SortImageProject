package main.listeners;

import com.mysql.cj.x.protobuf.MysqlxNotice;
import org.apache.catalina.SessionEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.*;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

/*
   first method to implement a listener - using @EventListener annotation
 */
@Component
public class ContextListenerAllEvents {
    @EventListener
    public void handleContextRefreshed (ContextRefreshedEvent event) {
        System.out.print(">>>> 1 >>> ContextRefreshedEvent event fired (class ContextListenerAllEvents)");
        System.out.println(event);
    }

    @EventListener
    public void handleContextStarted (ContextStartedEvent event) {
        System.out.print(">>>> 2 >>> ContextStartedEvent event fired (class ContextListenerAllEvents) ");
        System.out.println(event);
    }

    @EventListener
    public void handleContextStopped (ContextStoppedEvent event) {
        System.out.print(">>>> 3 >>> ContextStoppedEvent event fired (class ContextListenerAllEvents)");
        System.out.println(event);
    }

    @EventListener
    public void handleContextClosed (ContextClosedEvent event) {
        System.out.print(">>>> 4 >>> ContextClosedEvent event fired (class ContextListenerAllEvents)");
        System.out.println(event);
    }

    // does not work
    @EventListener
    public void handleSessionCreatedEvent (SessionCreatedEvent event) {
        System.out.print(">>>> 5 >>> SessionCreatedEvent fired: ");
        System.out.println(event);
    }

    // does not work
    @EventListener
    public void handleSessionDestroyedEvent (SessionDestroyedEvent event) {
        System.out.print(">>>> 6 >>> SessionDestroyedEvent fired: ");
        System.out.println(event);
    }
}
