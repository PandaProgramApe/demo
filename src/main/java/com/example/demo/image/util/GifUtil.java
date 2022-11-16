package com.example.demo.image.util;

import cn.hutool.core.img.gif.AnimatedGifEncoder;
import cn.hutool.core.img.gif.GifDecoder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class GifUtil {

    static Logger logger = LoggerFactory.getLogger(GifUtil.class);

    //图片压缩算法
    public static void PIC_Compression(String full_path) {
        try {
            Thumbnails.of(full_path)
                    .size(180, 180)
                    .toFile(full_path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean toTextGif(final String srcFile, final String targetFile, final String base, int threshold) {

        long startTime = System.currentTimeMillis();
        try {
            GifDecoder gd = new GifDecoder();
            // 要处理的图片
            int status = gd.read(new FileInputStream(new File(srcFile)));
            if (status != GifDecoder.STATUS_OK) {
                return false;
            }
            AnimatedGifEncoder ge = new AnimatedGifEncoder();
            // 这里是关键，设置要替换成透明的颜色
            ge.setTransparent(Color.WHITE);
            //
            ge.start(new FileOutputStream(new File(targetFile)));
            ge.setRepeat(0);
            System.out.println("------------" + gd.getFrameCount());

            GifDecoder gd2 = new GifDecoder();
            // 要处理的图片
            gd2.read(new FileInputStream("E:\\BaiduNetdiskDownload\\mygif\\1.gif"));
            for (int i = 0; i < gd2.getFrameCount(); i++) {
                BufferedImage frame = gd2.getFrame(i);
                BufferedImage image = Thumbnails.of(frame)
                        .size(600, 400).asBufferedImage();
                BufferedImage image1 = Thumbnails.of(image).scale(0.1f).outputQuality(0.02f).asBufferedImage();
                BufferedImage image2 = Thumbnails.of(image1).size(image1.getWidth(), image1.getHeight())
                        .watermark(Positions.TOP_LEFT, ImageIO.read(new File("E:\\BaiduNetdiskDownload\\mygif\\water.png")),
                                0.05f)
                        .asBufferedImage();
                int delay = gd2.getDelay(i);
                ge.setDelay(delay / 10);
                ge.addFrame(image2);
            }

            for (int i = 0; i < gd.getFrameCount(); i++) {
                // 取得gif的每一帧
                BufferedImage frame = gd.getFrame(i);
                BufferedImage image = Thumbnails.of(frame)
                        .size(600, 400).asBufferedImage();
                BufferedImage image1 = Thumbnails.of(image).scale(0.1f).outputQuality(0.02f).asBufferedImage();
                //水印 watermark(位置，水印图，透明度)
                BufferedImage image2 = Thumbnails.of(image1).size(image1.getWidth(), image1.getHeight())
                        .watermark(Positions.TOP_LEFT, ImageIO.read(new File("E:\\BaiduNetdiskDownload\\mygif\\water.png")),
                                0.05f)
                        .asBufferedImage();
//                // 你可以对每一帧做点什么，比如缩放什么的，这里就什么都不做了
//                int[] rgb = new int[3];
//                int width = frame.getWidth();
//                int height = frame.getHeight();
//                int minx = frame.getMinX();
//                int miny = frame.getMinY();
                int delay = gd.getDelay(i);
//                BufferedImage tag = ImageIO.read(file);
//                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
//                Graphics g = tag.getGraphics();
//                g.setFont(new Font("微软雅黑", Font.PLAIN, 2));// 设置字体
//                g.setColor(Color.BLACK);// 设置颜色
//                System.out.println("----------{},{} " + width + ":" + height);
//                for (int x = minx; x < width; x += 1) {
//                    for (int y = miny; y < height; y += 1) {
//                        int pixel = frame.getRGB(x, y); // 下面三行代码将一个数字转换为RGB数字
//                        rgb[0] = (pixel & 0xff0000) >> 16;// red
//                        rgb[1] = (pixel & 0xff00) >> 8;// green
//                        rgb[2] = (pixel & 0xff);// blue
//                        final float gray = 0.299F * rgb[0] + 0.578F * rgb[1] + 0.114F * rgb[2];
//                        // index [0,base.length()),index越小颜色越深
//                        final int index = Math.round(gray * (base.length() + 1) / 255);
//                        if (index <= base.length() % threshold) {
//                            g.drawString(String.valueOf(base.charAt(index % base.length())), x, y);// 文字的编写及位置
//                        }
//                        /*-
//                        if (rgb[0] + rgb[1] + rgb[2] &lt;= 300) {
//                            g.drawString(String.valueOf(base.charAt(index % base.length())), x, y);// 文字的编写及位置
//                        }*/
//                    }
//                }
                ge.setDelay(delay / 10);
                ge.addFrame(image2);
            }
            // 输出图片
            ge.finish();
            logger.debug("{} toTextGif cost time： {}s", srcFile, System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            logger.error("err", e);
            return false;
        }
        return true;
    }


    /**
     * 将文件转换为byte二进制流
     *
     * @param f
     * @return
     */
    public static byte[] getBytes(File f) {
        try {
            InputStream in = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = in.read(b)) != -1)
                out.write(b, 0, n);
            in.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            logger.error("***请设置文件路径***");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将gif格式图片转换为jpg格式文件，并直接返回byte二进制流
     *
     * @param file
     * @return
     */
    public static byte[] GifToJpg(File file) {
        BufferedImage bufferedImage;
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        try {
            // read image file
            bufferedImage = ImageIO.read(file);
            // create a blank, RGB, same width and height, and a white
            BufferedImage newBufferedImage = new BufferedImage(
                    bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", out);//转换输出到二进制数组流
            //ImageIO.write(newBufferedImage, "jpg",new File("c:\\java.jpg"));//转换输出到文件
            return out.toByteArray();//二进制流
        } catch (IOException e) {
            logger.error("***GifToJpg方法报错***");
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
//        if(fileName.toLowerCase().endsWith(".gif")){//由于头像上传支持JPG、JPEG、BMP、GIF、PNG格式图片.而商汤人脸设备仅支持JPG、JPEG、BMP、PNG,故如图片为GIF格式需要转换
//            fileParams.put("avatarFile", api.GifToJpg(avatar_file));
//        }else{
//            fileParams.put("avatarFile", apz`i.getBytes(avatar_file));
//        }
        String srcFile = "E:\\BaiduNetdiskDownload\\mygif\\2.gif";
        String targetFile = "E:\\BaiduNetdiskDownload\\mygif\\11.gif";
        String base = "01"; // 替换的字符串
        // String base = "@#&amp;$%*o!;.";// 字符串由复杂到简单
        int threshold = 3;// 阈值
        GifUtil.toTextGif(srcFile, targetFile, base, threshold);
    }
}