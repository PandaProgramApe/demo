package com.example.demo.image.util;


import cn.hutool.core.img.gif.AnimatedGifEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PageTest {

    public static void main(String[] args) throws IOException {
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start("D:/gif.gif");
        encoder.setTransparent(Color.WHITE);
        encoder.setRepeat(0);
        encoder.setDelay(50);

        BufferedImage img = new BufferedImage(200, 180, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = img.createGraphics();

        for (int i = 0; i < 100; i++) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, 200, 180);
            g2d.setColor(Color.BLUE);
            g2d.drawOval(0, i, 120, 120);
            encoder.addFrame(img);
        }
        g2d.dispose();
        encoder.finish();
    }

}