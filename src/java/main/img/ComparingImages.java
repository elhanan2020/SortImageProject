package main.img;

import main.beans.MySession;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ComparingImages extends Thread {
    private final String directoryPath;

    public ArrayList<ArrayList<BufferedImage>> getArrImg() {
        return arrImg;
    }

    public void setArrImg(ArrayList<ArrayList<BufferedImage>> arrImg) {
        this.arrImg = arrImg;
    }

    private  ArrayList<ArrayList<BufferedImage>> arrImg;

    public ComparingImages(String username , ArrayList<ArrayList<BufferedImage>> session) {
        arrImg = session;
        this.directoryPath = new String("\\Users\\owner\\myNewFile\\");

    }



    public void run() {
        try {
            arrImg = compareImg();
            //checkArr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public ArrayList<ArrayList<BufferedImage>> compareImg() throws Exception {
        ArrayList<String> cont = new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File (directoryPath).list())));
        boolean on;
        ArrayList<ArrayList<BufferedImage>> arrTemp = new ArrayList<>();
        ArrayList<Integer> toClear = new ArrayList<>();
        if(cont.isEmpty())
            return arrTemp;
        for (int img = 0; img < cont.size(); img++) {
            on = false;
            ArrayList<BufferedImage> Temp = new ArrayList<>();
            BufferedImage img1 = ImageIO.read(new File(directoryPath + cont.get(img)));
            for (int imge = img + 1; imge < cont.size(); imge++) {
                BufferedImage img2 = ImageIO.read(new File(directoryPath + cont.get(imge)));
                int w1 = img1.getWidth();
                int w2 = img2.getWidth();
                int h1 = img1.getHeight();
                int h2 = img2.getHeight();
                if ((w1 != w2) || (h1 != h2)) {
                    System.out.println("Both images should have same dimensions");
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
                    double percentage = (avg / 255) * 100;
                    if(percentage <= 10.0 ){
                        on = true;
                        Temp.add(ImageIO.read(new File(directoryPath + cont.get(imge))));
                        Files.delete(Paths.get("C:\\Users\\owner\\myNewFile\\" + cont.get(imge)));
                        cont.remove(imge);
                    }
                }
            }
            if(on){
                Temp.add(ImageIO.read(new File(directoryPath + cont.get(img))));
                arrTemp.add(Temp);
                toClear.add(img);
            }
        }
        for (int i = toClear.size() - 1; i >= 0; i--){
            Files.delete(Paths.get("C:\\Users\\owner\\myNewFile\\" + cont.get(toClear.get(i))));
            cont.remove(toClear.get(i));
        }

        return arrTemp;
    }
















    public  void checkArr() {
        for (int numImg = 0; numImg < arrImg.size(); numImg++) {
            for (int img = 0; img < arrImg.get(numImg).size(); img++) {
                for (int imge = img + 1; imge < arrImg.get(numImg).size(); imge++) {
                    BufferedImage img1 = arrImg.get(numImg).get(img);
                    BufferedImage img2 = arrImg.get(numImg).get(imge);
                    int w1 = img1.getWidth();
                    int w2 = img2.getWidth();
                    int h1 = img1.getHeight();
                    int h2 = img2.getHeight();
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
                    double percentage = (avg / 255) * 100;
                    if (percentage <= 0.3) {
                        arrImg.remove(imge);
                    }
                }
            }
        }
    }
}