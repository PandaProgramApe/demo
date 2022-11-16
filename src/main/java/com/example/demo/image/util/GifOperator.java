package com.example.demo.image.util;

import cn.hutool.core.img.gif.AnimatedGifEncoder;
import cn.hutool.core.img.gif.GifDecoder;
import cn.hutool.core.util.StrUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GifOperator {

    public static void main(String[] args) throws IOException {

//        String outputPath = "D:/testyasuo/1.gif";
//        String imagePath = "D:/testgif33.gif";
//        reverseGif(imagePath,outputPath);
        // Gif转图片
//        String dirPath = "/home/lab/test/22/";
//        gifToImages(imagePath,dirPath);
        List<File> allFile = getAllFile("C:\\Users\\Administrator\\PycharmProjects\\image\\性感美女");
        List<BufferedImage> images = new ArrayList<>();
        for(File file : allFile) {
            BufferedImage image = ImageIO.read(file);
            images.add(image);
        }
//        for (int i = 0 ; i < allFile.;i++) {
//            File outFile = new File(dirPath + i + ".png");
//            BufferedImage image = ImageIO.read(outFile);
//            images.add(image);
//        }
        imagesToGif(images,"D:/home/test/res.gif");
    }


    /**
     * 获取指定文件夹下所有文件，不含文件夹里的文件
     *
     * @param dirFilePath 文件夹路径
     * @return
     */
    public static List<File> getAllFile(String dirFilePath) {
        if (StrUtil.isBlank(dirFilePath))
            return null;
        return getAllFile(new File(dirFilePath));
    }


    /**
     * 获取指定文件夹下所有文件，不含文件夹里的文件
     *
     * @param dirFile 文件夹
     * @return
     */
    public static List<File> getAllFile(File dirFile) {
        // 如果文件夹不存在或着不是文件夹，则返回 null
        if (Objects.isNull(dirFile) || !dirFile.exists() || dirFile.isFile())
            return null;

        File[] childrenFiles = dirFile.listFiles();
        if (Objects.isNull(childrenFiles) || childrenFiles.length == 0)
            return null;

        List<File> files = new ArrayList<>();
        for (File childFile : childrenFiles) {
            // 如果是文件，直接添加到结果集合
            if (childFile.isFile()) {
                files.add(childFile);
            }
            //以下几行代码取消注释后可以将所有子文件夹里的文件也获取到列表里
//            else {
//                // 如果是文件夹，则将其内部文件添加进结果集合
//                List<File> cFiles = getAllFile(childFile);
//                if (Objects.isNull(cFiles) || cFiles.isEmpty()) continue;
//                files.addAll(cFiles);
//            }
        }
        return files;
    }


    /**
     * 多图片转gif
     * @param imageList
     * @param outputPath
     * @throws IOException
     */
    static void imagesToGif(List<BufferedImage> imageList, String outputPath) throws IOException {
        // 拆分一帧一帧的压缩之后合成
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(outputPath);
        encoder.setRepeat(0);
        for (BufferedImage bufferedImage :
                imageList) {
            encoder.setDelay(100);
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            BufferedImage zoomImage = new BufferedImage(width, height, 3);
            Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            Graphics gc = zoomImage.getGraphics();
            gc.setColor(Color.WHITE);
            gc.drawImage(image, 0, 0, null);
            encoder.addFrame(zoomImage);
        }
        encoder.finish();
        File outFile = new File(outputPath);
        BufferedImage image = ImageIO.read(outFile);
        ImageIO.write(image, outFile.getName(), outFile);
    }

    /**
     * Gif转图片集
     * @param imagePath
     * @param outputDirPath
     * @throws IOException
     */
    static void gifToImages(String imagePath,String outputDirPath) throws IOException {
        GifDecoder decoder = new GifDecoder();
        int status = decoder.read(imagePath);
        if (status != GifDecoder.STATUS_OK) {
            throw new IOException("read image " + imagePath + " error!");
        }
        for (int i = 0; i < decoder.getFrameCount();i++) {
            BufferedImage bufferedImage = decoder.getFrame(i);// 获取每帧BufferedImage流
            File outFile = new File(outputDirPath + i + ".png");
            ImageIO.write(bufferedImage, "png", outFile);
        }
    }

    /**
     * 视频倒放
     * @param imagePath
     * @param outputPath
     * @throws IOException
     */
    public static void reverseGif(String imagePath,String outputPath) throws IOException {
        GifDecoder decoder = new GifDecoder();
        int status = decoder.read(imagePath);
//        if (status != GifDecoder.STATUS_OK) {
//            throw new IOException("read image " + imagePath + " error!");
//        }
        // 拆分一帧一帧的压缩之后合成
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(outputPath);
        encoder.setRepeat(decoder.getLoopCount());
        for (int i = decoder.getFrameCount() -1; i >= 0; i--) {
            encoder.setDelay(decoder.getDelay(i));// 设置播放延迟时间
            BufferedImage bufferedImage = decoder.getFrame(i);// 获取每帧BufferedImage流
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            BufferedImage zoomImage = new BufferedImage(width, height, bufferedImage.getType());
            Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            Graphics gc = zoomImage.getGraphics();
            gc.setColor(Color.WHITE);
            gc.drawImage(image, 0, 0, null);
            encoder.addFrame(zoomImage);
        }
        encoder.finish();
        File outFile = new File(outputPath);
        BufferedImage image = ImageIO.read(outFile);
        ImageIO.write(image, outFile.getName(), outFile);
    }

}