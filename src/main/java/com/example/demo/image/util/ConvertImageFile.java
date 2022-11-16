package com.example.demo.image.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConvertImageFile {
    public static void main(String[] args) {
        BufferedImage bufferedImage;
        try {
            //read image file
            bufferedImage = ImageIO.read(new File("c:\\java.png"));
            // create a blank, RGB, same width and height, and a white background
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            //TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", new File("c:\\java.jpg"));
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}