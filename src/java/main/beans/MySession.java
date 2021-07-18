package main.beans;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * in this class i  initialize a claas label that store any field that i want to store in session,context...
 */
@Component
public class MySession  implements Serializable {
    private boolean connected ;
    private String userName="";


    ArrayList<ArrayList<BufferedImage>> arrImg ;


    private String label = "Arbitrary Label";
    public MySession() {
    this.arrImg =  new ArrayList<>();
        connected = false;
    }

    @Override
    public String toString() {
        return "MySession{" +
                "connected=" + connected +
                ", userName='" + userName + '\'' +
                ", arrImg=" + arrImg +
                ", label='" + label + '\'' +
                '}';
    }

    public ArrayList<ArrayList<BufferedImage>> getArrImg() {
        return arrImg;
    }

    public void setArrImg(ArrayList<ArrayList<BufferedImage>> arrImg) {
        this.arrImg = arrImg;
    }

    /**
     * initialize the variable connected in true if connect or false if disconnected
     *
     */
    public void setConnected(boolean l) {
        this.connected = l;
    }

    /**
     * initialize username
     * @param name name of user
     */
    public void setUserName(String name) {
        this.userName = name;
    }

    /**
     * return if is connect or no
     * @return this.connected
     */
    public boolean getConnected() {
        return this.connected;
    }

    /**
     * return the user name
     * @return this.userName
     */
    public String getUserName() {
        return this.userName;
    }

}