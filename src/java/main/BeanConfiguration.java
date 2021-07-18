package main;
import main.beans.MySession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;


/**
 * create some beans witn various scopes using QUALIFIERS (method names)
 */
@Configuration
public class BeanConfiguration {



    @Bean
    @SessionScope
    public MySession sessionBean () {
        return new MySession();
    }
}
