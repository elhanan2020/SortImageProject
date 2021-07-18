package main.filters;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * this class intercepts all requests and displays statistics: request processing duration
 *
 * it also demonstrates how to inject a bean (you cannot use Spring @Autowired in a
 * HandlerInterceptor but you can receive the bean via the ctor - from the configuration class)
 */

public class LoggingInterceptor implements HandlerInterceptor {

    public LoggingInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long startTime = System.currentTimeMillis();

        //System.out.print("-------- preHandle --- ");
        //System.out.print("Request URL: " + request.getRequestURL());
        //System.out.println("; Start Time: " + System.currentTimeMillis());

        request.setAttribute("startTime", startTime);

        // filter can redirect response to a specific page
        // response.sendRedirect("/error");

        // return FALSE will block the request chaining!

        return true; // continue with the request to next filter or to controller
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, //
                           Object handler, ModelAndView modelAndView) throws Exception {

        //System.out.print("-------- postHandle ---: ");
        //System.out.println("Request URL: " + request.getRequestURL());

        // You can add attributes in the modelAndView
        // and use that in the view page
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, //
                                Object handler, Exception ex) throws Exception {
        //System.out.print("-------- afterCompletion ---: ");

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        System.out.print("Request URL: " + request.getRequestURL());
        //System.out.println("; End Time: " + endTime);

        System.out.println("Time Taken: " + (endTime - startTime));
    }

}