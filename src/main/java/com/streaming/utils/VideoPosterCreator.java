package com.streaming.utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class VideoPosterCreator {
    public static void createPoster (String videoName, String imageName) throws IOException {
        FFmpegFrameGrabber g = new FFmpegFrameGrabber("D:\\swapnika\\streaming\\streaming\\src\\main\\resources\\static\\videos\\" + videoName + ".mp4");
        g.start();

        ImageIO.write(new Java2DFrameConverter().convert(g.grabKeyFrame()), "png", new File("static/images/" + imageName + ".png"));

        g.stop();
    }

   /* public static void main (String[] args) {
        try {
            VideoPosterCreator.createPoster("sample", "sample");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
