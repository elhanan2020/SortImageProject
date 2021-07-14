package main.listeners;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class SessionCreatedListener implements ApplicationListener<SessionCreatedEvent> {
//        ,ApplicationListener<SessionDestroyedEvent> {

    private final AtomicInteger counter = new AtomicInteger();

    private void updateSessionCounter(HttpSessionEvent e){
        //Let's set in the context
        e.getSession().getServletContext()
                .setAttribute("activeSession", counter.get());
        System.out.println("!!!! Total active session are " + counter.get());
    }

    @Override
    public void onApplicationEvent(SessionCreatedEvent sce) {
        counter.incrementAndGet();
        System.out.println("!!!! Total active session are " + counter.get());
    }

    //@Override
//    public void onApplicationEvent(SessionDestroyedEvent sessionDestroyedEvent) {
//        counter.decrementAndGet();
//        System.out.println("Total active session are {} " + counter.get());
//    }
}