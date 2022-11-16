package com.example.demo.image.util;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
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

public class MyTest {

//    public static void main(String[] args) throws IOException, InterruptedException {
//
//        List<File> allFile = getAllFile("C:\\image\\out");
//        List<BufferedImage> images = new ArrayList<>();
//        for (File file : allFile) {
//            // 图片压缩
//            picCompression(file.getAbsolutePath());
//
//            // 添加水印
//            BufferedImage image = ImageIO.read(file);
//            waterMark(image, file.getAbsolutePath());
//
//            BufferedImage image2 = ImageIO.read(file);
//            images.add(image2);
//        }
//        AnimatedGifEncoder e = new AnimatedGifEncoder();
//        //生成的图片路径
//        e.start(new FileOutputStream("D:/66666.gif"));
//        //图片之间间隔时间
//        e.setDelay(300);
//        //重复次数 0表示无限重复 默认不重复
//        e.setRepeat(0);
//        //添加图片
//        for (BufferedImage image : images) {
//            e.addFrame(image);
//        }
//        e.finish();
//    }

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


    @SneakyThrows
    public static void main(String[] args) {
        BufferedImage image = ImageIO.read(new File("F://test.jpg"));
        waterMark(image, "F://test.jpg");
    }

    /**
     * 水印添加
     *
     * @param srcImg
     * @param path
     * @throws IOException
     */
    private static void waterMark(Image srcImg, String path) throws IOException {
        int srcImgWidth = srcImg.getWidth(null);
        //获取图片的高
        int srcImgHeight = srcImg.getHeight(null);
        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        //创建画笔
        Graphics2D g = bufImg.createGraphics();
        //绘制原始图片
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
//        // 水印文件地址
//        Image srcWaterMark = ImageIO.read(new File("D:/gif/WaterMark.png"));
//        //获取水印图片的宽度
//        int widthWaterMark = srcWaterMark.getWidth(null);
//        //获取水印图片的高度
//        int heightWaterMark = srcWaterMark.getHeight(null);
//        //设置 alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
//        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.9f));
//        //绘制水印图片坐标
//        g.drawImage(srcWaterMark, (srcImgWidth - widthWaterMark) / 20,
//                (srcImgHeight - heightWaterMark) / 20, widthWaterMark, heightWaterMark, null);
//        // 水印文件结束
//        g.dispose();
        //根据图片的背景设置水印颜色
        g.setColor(new Color(255, 255, 255, 128));
        //设置字体  画笔字体样式为微软雅黑，加粗，文字大小为60pt
        g.setFont(new Font("微软雅黑", Font.ITALIC, 30));
        int x = srcImgWidth / 2;
        int y = srcImgHeight / 2;
        //画出水印 第一个参数是水印内容，第二个参数是x轴坐标，第三个参数是y轴坐标
        g.drawString("中国", x, y);
        g.dispose();
        //待存储的地址
        String tarImgPath = path;
        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
        ImageIO.write(bufImg, "png", outImgStream);
        outImgStream.flush();
        outImgStream.close();
    }


    /**
     * 图片压缩处理
     *
     * @param full_path
     */
    public static void picCompression(String full_path) {
        try {
            Thumbnails.of(full_path)
                    .size(180, 180)
                    .toFile(full_path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
