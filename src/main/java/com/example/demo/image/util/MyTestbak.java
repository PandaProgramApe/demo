package com.example.demo.image.util;

import cn.hutool.core.img.gif.AnimatedGifEncoder;
import cn.hutool.core.util.StrUtil;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyTestbak {

    public static void main(String[] args) throws IOException, InterruptedException {

        List<File> allFile = getAllFile("C:\\Users\\Administrator\\PycharmProjects\\image\\out");
        List<BufferedImage> images = new ArrayList<>();
        for (File file : allFile) {
            //yasuo
            PIC_Compression(file.getAbsolutePath());
//            Thumbnails.of(file.getAbsolutePath()).scale(0.5f).outputQuality(0.5f).toFile(file.getAbsolutePath());
            BufferedImage image = ImageIO.read(file);
            extracted(image, file.getAbsolutePath());
            BufferedImage image2 = ImageIO.read(file);
            images.add(image2);
        }
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        //生成的图片路径
        e.start(new FileOutputStream("D:/nihao6.gif"));
        //图片之间间隔时间
        //        e.setSize(500, 889);
        e.setDelay(300);
        //重复次数 0表示无限重复 默认不重复
        e.setRepeat(0);
        //添加图片
//            e.addFrame(image1);
        for (BufferedImage image : images) {
            e.addFrame(image);
        }
        e.finish();
    }

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
        }
        return files;
    }

    private static void extracted(Image srcImg, String path) throws IOException {
        int srcImgWidth = srcImg.getWidth(null);
        //获取图片的高
        int srcImgHeight = srcImg.getHeight(null);
        System.out.println("图片的宽:" + srcImgWidth);
        System.out.println("图片的高:" + srcImgHeight);

        //创建一个URL对象,获取网络图片的地址信息（网络图片）
//        URL url = new URL("https://pngimg.com/distr/img/ukraine.png");
//        //将URL对象输入流转化为图片对象 (url.openStream()方法，获得一个输入流)
//        Image srcImg = ImageIO.read(url.openStream());
//        //获取图片的宽
//        int srcImgWidth = srcImg.getWidth(null);
//        //获取图片的高
//        int srcImgHeight = srcImg.getHeight(null);
//        System.out.println("图片的宽:"+srcImgWidth);
//        System.out.println("图片的高:"+srcImgHeight);


        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        // 加水印
        //创建画笔
        Graphics2D g = bufImg.createGraphics();
        //绘制原始图片
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
        //-------------------------文字水印 start----------------------------
//        //根据图片的背景设置水印颜色
//        g.setColor(new Color(255,255,255,128));
//        //设置字体  画笔字体样式为微软雅黑，加粗，文字大小为60pt
//        g.setFont(new Font("微软雅黑", Font.BOLD, 60));
//        String waterMarkContent="图片来源：https://image.baidu.com/";
//        //设置水印的坐标(为原图片中间位置)
//        int x=(srcImgWidth - getWatermarkLength(waterMarkContent, g)) / 2;
//        int y=srcImgHeight / 2;
//        //画出水印 第一个参数是水印内容，第二个参数是x轴坐标，第三个参数是y轴坐标
//        g.drawString(waterMarkContent, x, y);
//        g.dispose();
        //-------------------------文字水印 end----------------------------

        //-------------------------图片水印 start----------------------------
        // 水印文件
        String waterMarkImage = "D:/gif/print.jpg";
        Image srcWaterMark = ImageIO.read(new File(waterMarkImage));
        //获取水印图片的宽度
        int widthWaterMark = srcWaterMark.getWidth(null);
        //获取水印图片的高度
        int heightWaterMark = srcWaterMark.getHeight(null);
        //设置 alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.9f));
        //绘制水印图片  坐标为中间位置
        g.drawImage(srcWaterMark, (srcImgWidth - widthWaterMark) / 20,
                (srcImgHeight - heightWaterMark) / 20, widthWaterMark, heightWaterMark, null);
        // 水印文件结束
        g.dispose();
        //-------------------------图片水印 end----------------------------

        //待存储的地址
        String tarImgPath = path;
        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
        ImageIO.write(bufImg, "png", outImgStream);
        System.out.println("添加水印完成");
        outImgStream.flush();
        outImgStream.close();
    }

//    public static void main(String[] args) {
//        PIC_Compression("D:/gif/2.jpg");
//    }

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
}
