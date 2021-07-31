package main.beans;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * in this class i  initialize a claas label that store any field that i want to store in session,context...
 */
@Component
public class MySession  implements Serializable {
    private boolean connected ;
    private String userName="";

    public void setFinish(boolean finish) {
        this.finish[0] = finish;
    }

    private boolean[] finish = new boolean[]{true};
    public void setArrTemp(ArrayList<ArrayList<String>> arrTemp) {
        this.arrTemp = arrTemp;
    }
    public ArrayList<ArrayList<String>> getArrTemp() {
        return arrTemp;
    }

    private ArrayList<ArrayList<String>> arrTemp = new ArrayList<>();



    HashMap<String, BufferedImage> myMapImg ;


    private String label = "Arbitrary Label";
    public MySession() {
    this.myMapImg =  new HashMap<>();
        connected = false;
    }

    @Override
    public String toString() {
        return "MySession{" +
                "connected=" + connected +
                ", userName='" + userName + '\'' +
                ", arrImg=" + myMapImg +
                ", label='" + label + '\'' +
                '}';
    }

    public HashMap<String, BufferedImage> getArrImg() {
        return myMapImg;
    }

    public void setArrImg(HashMap<String, BufferedImage> arrImg) {
        this.myMapImg = arrImg;
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

    public void reset() {
        connected = false;
        userName = "";
        myMapImg = new HashMap<>();
    }

    public boolean[] isFinish() {
        return finish;
    }
}