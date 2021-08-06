package main.controllers;
import main.beans.MySession;
import main.utils.UploadFileProperties;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageReturnController {



    @Resource(name = "sessionBean")
    public MySession sessionObj;

    @Autowired
    UploadFileProperties uploadFileProperties;

    @GetMapping(value ="/get-image/{imgNum1}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable("imgNum1") String imgNum ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage thumbnail = Scalr.resize(sessionObj.getArrImg().get(imgNum),  Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
                600, 400, Scalr.OP_ANTIALIAS);
        ImageIO.write(thumbnail, "JPEG", baos);
        baos.flush();
        return baos.toByteArray();
    }


    @GetMapping(value ="/getAllImage/{imgNum1}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getAllImage(@PathVariable("imgNum1") String imgNum ) throws IOException {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage thumbnail = Scalr.resize(ImageIO.read(new File(myPath + "\\" + sessionObj.getUserName() +"\\" + imgNum)),  Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
                600, 400, Scalr.OP_ANTIALIAS);
        ImageIO.write(thumbnail, "JPEG", baos);
        baos.flush();
        return baos.toByteArray();
    }
}