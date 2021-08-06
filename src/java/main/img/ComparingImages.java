package main.img;


import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ComparingImages extends Thread {
    private final boolean[] finish;
    private final String directoryPath;
    private final ArrayList<ArrayList<String>> arrTemp  ;
    private final String action  ;
    private  HashMap<String, BufferedImage> myMap;


    public HashMap<String, BufferedImage> getArrImg() {
        return myMap;
    }

    public void setArrImg(HashMap<String, BufferedImage> myMap) {
        this.myMap = myMap;
    }


    public ComparingImages(String path,String username , HashMap<String, BufferedImage> session,ArrayList<ArrayList<String>> arrTemp,boolean[] finish,String action) {
        this.arrTemp = arrTemp;
        this.myMap = session;
        this.directoryPath = new String(path+"\\" + username+"\\");
        this.action = action;
        this.finish = finish;
    }



    public void run() {
        finish[0]= false;
        try {
            switch (action) {
                case "SortingAndFiltering" :
                    compareImg();
                    checkArr();
                case "SORTING":
                    compareImg();
                case "FILTERING":
                    compareAndRemoveImg();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finish[0] = true;
    }



    public void compareImg() throws Exception {
        ArrayList<String> cont = new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File (directoryPath).list())));
        ArrayList<Integer> toClear = new ArrayList<>();
        boolean on;
        if(cont.isEmpty())
            return;
        for (int img = 0; img < cont.size(); img++) {
            on = false;
            ArrayList<String> Temp = new ArrayList<>();
            BufferedImage img1 = ImageIO.read(new File(directoryPath + cont.get(img)));
            for (int imge = img + 1; imge < cont.size(); imge++) {
                BufferedImage img2 = ImageIO.read(new File(directoryPath + cont.get(imge)));
                    double percentage = checkImg(img1,img2);
                    if(percentage <= 10.0 ){
                        on = true;
                        myMap.put(cont.get(imge),ImageIO.read(new File(directoryPath + cont.get(imge))));
                        Temp.add(cont.get(imge));
                        Files.delete(Paths.get(directoryPath + cont.get(imge)));
                        cont.remove(imge);
                    }
            }
            if(on){
                myMap.put(cont.get(img),ImageIO.read(new File(directoryPath + cont.get(img))));
                Temp.add(cont.get(img));
                arrTemp.add(Temp);
                toClear.add(img);
            }
        }
        for (int i = toClear.size() - 1; i >= 0; i--){
            Files.delete(Paths.get(directoryPath + cont.get(toClear.get(i))));
            cont.remove(toClear.get(i));
        }
    }
    public void compareAndRemoveImg() throws Exception {
        ArrayList<String> cont = new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File (directoryPath).list())));
        if(cont.isEmpty())
            return;
        for (int img = 0; img < cont.size(); img++) {
            BufferedImage img1 = ImageIO.read(new File(directoryPath + cont.get(img)));
            for (int imge = img + 1; imge < cont.size(); imge++) {
                BufferedImage img2 = ImageIO.read(new File(directoryPath + cont.get(imge)));
                    double percentage = checkImg(img1,img2);
                    if(percentage == 0.0 ){
                        Files.delete(Paths.get(directoryPath + cont.get(imge)));
                        cont.remove(imge);
                        imge--;
                    }
            }
        }
    }

    private double checkImg(BufferedImage img1,BufferedImage img2) {
        int w1 = img1.getWidth();
        int w2 = img2.getWidth();
        int h1 = img1.getHeight();
        int h2 = img2.getHeight();
        if ((w1 != w2) || (h1 != h2)) {
            return 100.0;
        } else {
            long diff = 0;
            for (int j = 0; j < h1; j++) {
                for (int i = 0; i < w1; i++) {
                    //Getting the RGB values of a pixel
                    int pixel1 = img1.getRGB(i, j);
                    Color color1 = new Color(pixel1, true);
                    int r1 = color1.getRed();
                    int g1 = color1.getGreen();
                    int b1 = color1.getBlue();
                    int pixel2 = img2.getRGB(i, j);
                    Color color2 = new Color(pixel2, true);
                    int r2 = color2.getRed();
                    int g2 = color2.getGreen();
                    int b2 = color2.getBlue();
                    //sum of differences of RGB values of the two images
                    long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                    diff = diff + data;
                }
            }
            double avg = diff / (w1 * h1 * 3);
            return (avg / 255) * 100;
        }

    }

    public void checkArr() throws IOException {
        for (ArrayList<String> strings : arrTemp)
        {
            for (int numImg = 0; numImg < strings.size(); numImg++)
            {
                for (int img = numImg + 1; img < strings.size(); img++) {
                    BufferedImage img1 = myMap.get(strings.get(numImg));
                    BufferedImage img2 = myMap.get(strings.get(img));
                    double percentage = checkImg(img1, img2);
                    if (percentage == 0.0) {
                        myMap.remove(strings.get(img));
                        strings.remove(img);
                        img--;
                    }
                }
            }
        }
        for (int i = 0;i< arrTemp.size();i++) {
            if (arrTemp.get(i).size()==1){
                File outfile = new File(directoryPath + arrTemp.get(i).get(0));
                ImageIO.write(myMap.get(arrTemp.get(i).get(0)),arrTemp.get(i).get(0).split("\\.")[1],outfile);
                myMap.remove(arrTemp.get(i).get(0));
                arrTemp.remove(i);
                i--;
            }
        }
    }

}