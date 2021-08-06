package main.beans;

import org.springframework.stereotype.Component;

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
    private String userName = "";

    public void setFinish(boolean finish) {
        this.finish[0] = finish;
    }

    private final boolean[] finish = new boolean[]{true};
    public void setArrTemp(ArrayList<ArrayList<String>> arrTemp) {
        this.arrTemp = arrTemp;
    }
    public ArrayList<ArrayList<String>> getArrTemp() {
        return arrTemp;
    }

    private ArrayList<ArrayList<String>> arrTemp = new ArrayList<>();

    public void setNumOfInitialPicture(Integer numOfInitialPicture) {
        NumOfInitialPicture = numOfInitialPicture;
    }

    private  Integer  NumOfInitialPicture ;

    public Integer getNumOfInitialPicture() {
        return NumOfInitialPicture;
    }

    public Integer getNumOfFinalPicture() {
        return NumOfFinalPicture;
    }

    public void setNumOfFinalPicture(Integer numOfFinalPicture) {
        NumOfFinalPicture = numOfFinalPicture;
    }

    private  Integer  NumOfFinalPicture ;

    private HashMap<String, BufferedImage> myMapImg ;


    private String label = "Arbitrary Label";

    public MySession() {
        NumOfFinalPicture = 0;
        NumOfInitialPicture = 0;
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


    public void setConnected(boolean l) {
        this.connected = l;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public boolean getConnected() {
        return this.connected;
    }


    public String getUserName() {
        return this.userName;
    }

    public void reset(boolean cmd) {
        if(!cmd)
            connected = false;
        myMapImg = new HashMap<>();
        arrTemp = new ArrayList<>();
    }

    public boolean[] isFinish() {
        return finish;
    }
}