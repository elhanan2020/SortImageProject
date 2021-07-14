package main.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.ArrayList;

/* this is a simple bean class instantiated in session
  it contains a list of strings
*/
@Component
public class Messages implements Serializable {
    private ArrayList<String> messages;

    public Messages() {
        this.messages = new ArrayList<>();
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public void add (String m) {
        messages.add(m);
    }

}