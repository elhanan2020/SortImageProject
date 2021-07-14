package main.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/*
  second method to create a listener: implementing an Spring interface
 */
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    // for tests
    private boolean hitContextRefreshedHandler = false;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent cse) {
        System.out.println("**** ContextRefreshedEvent event fired (class ContextRefreshedListener)");
        hitContextRefreshedHandler = true;
    }

    boolean isHitContextRefreshedHandler() {
        return hitContextRefreshedHandler;
    }
}