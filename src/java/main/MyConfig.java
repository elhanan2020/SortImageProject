package main;

import main.beans.MySession;
import main.filters.LoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * its the class that is configure the filter to ajax request
 */

@Configuration
@EnableWebMvc
public class MyConfig implements WebMvcConfigurer {


    @Resource(name = "sessionBean")
    public MySession sessionObj;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor(sessionObj)).addPathPatterns("/showResultOfResearch/**","/upload", "/gallery", "/logOut");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }


}