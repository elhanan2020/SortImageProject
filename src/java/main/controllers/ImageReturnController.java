package main.controllers;
import main.beans.MySession;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Controller
public class ImageReturnController {



    @Resource(name = "sessionBean")
    public MySession sessionObj;


    @GetMapping(value ="/get-image/{imgNum1}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable("imgNum1") String imgNum ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage thumbnail = Scalr.resize(sessionObj.getArrImg().get(imgNum),  Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
                600, 400, Scalr.OP_ANTIALIAS);
        ImageIO.write(thumbnail, "JPEG", baos);
        baos.flush();
        return baos.toByteArray();
    }

   /* @GetMapping(value = "/get-image-with-media-type", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        final InputStream in = getClass().getResourceAsStream("main/controllers/ImageReturnController/image.jpg");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/get-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile() throws IOException {
        final InputStream in = getClass().getResourceAsStream("/com/baeldung/produceimage/data.txt");
        return IOUtils.toByteArray(in);
    }*/

}