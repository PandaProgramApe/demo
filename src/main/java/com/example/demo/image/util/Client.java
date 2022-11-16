package com.example.demo.image.util;

import cn.hutool.core.img.gif.AnimatedGifEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

public class Client {

    public static void main(String[] args) throws Exception {
//            BufferedImage image1 = ImageIO.read(new File("D:/me/555.jpg"));
        BufferedImage image2 = ImageIO.read(new File("D:/me/666.jpg"));
        BufferedImage image3 = ImageIO.read(new File("D:/me/777.jpg"));
        BufferedImage image4 = ImageIO.read(new File("D:/me/888.jpg"));
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        //生成的图片路径
        e.start(new FileOutputStream("D:/99999999999.gif"));
        //图片之间间隔时间
        //        e.setSize(500, 889);
        e.setDelay(1000);
        //重复次数 0表示无限重复 默认不重复
        e.setRepeat(0);
        //添加图片
//            e.addFrame(image1);
        e.addFrame(image2);
        e.addFrame(image3);
        e.addFrame(image4);
        e.finish();

//            GifImage srcImage = GifDecoder.decode(src);
//            GifImage scaleImg = GifTransformer.scale(srcImage, wQuotiety, hQuotiety, true);
//            GifEncoder.encode(scaleImg, dest);

    }

}