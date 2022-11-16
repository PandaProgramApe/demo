package com.example.demo.image.util;

import cn.hutool.core.img.gif.AnimatedGifEncoder;
import cn.hutool.core.img.gif.GifDecoder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class ImageUtils {

    public enum IMAGE_FORMAT {
        BMP("bmp"),
        JPG("jpg"),
        WBMP("wbmp"),
        JPEG("jpeg"),
        PNG("png"),
        GIF("gif");

        private String value;

        IMAGE_FORMAT(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

//    public static void main(String[] args) throws IOException {
//        // 读取原图片信息 得到文件（本地图片）
//        File srcImgFile = new File("D:/gif/1.jpg");
//        //将文件对象转化为图片对象
//        Image srcImg = ImageIO.read(srcImgFile);
//        //获取图片的宽
//        extracted(srcImg, srcImgFile.getAbsolutePath());
//
//    }


    private static void extracted(Image srcImg, String path) throws IOException {
        int srcImgWidth = srcImg.getWidth(null);
        //获取图片的高
        int srcImgHeight = srcImg.getHeight(null);
        System.out.println("图片的宽:"+srcImgWidth);
        System.out.println("图片的高:"+srcImgHeight);

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
        String waterMarkImage="D:/gif/print.jpg";
        Image srcWaterMark = ImageIO.read(new File(waterMarkImage));
        //获取水印图片的宽度
        int widthWaterMark= srcWaterMark.getWidth(null);
        //获取水印图片的高度
        int heightWaterMark = srcWaterMark.getHeight(null);
        //设置 alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.9f));
        //绘制水印图片  坐标为中间位置
        g.drawImage(srcWaterMark, (srcImgWidth - widthWaterMark) / 10,
                (srcImgHeight - heightWaterMark) / 10, widthWaterMark, heightWaterMark, null);
        // 水印文件结束
        g.dispose();
        //-------------------------图片水印 end----------------------------

        //待存储的地址
        String tarImgPath= path;
        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
        ImageIO.write(bufImg, "png", outImgStream);
        System.out.println("添加水印完成");
        outImgStream.flush();
        outImgStream.close();
    }

    /**
     * 获取水印文字的长度
     * @param waterMarkContent
     * @param g
     * @return
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    /**
     * 获取文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     * @throws IOException
     */
    public static String getFileName(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("file not exist!");
        }
        return file.getName();
    }

    /**
     * 1、自定义图片
     *
     * @param args
     */
//    @SneakyThrows
//    public static void main(String[] args) {
//        BufferedImage bufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_BGR);
//        Graphics g = bufferedImage.getGraphics();
//        try {
//            g.fillRect(20, 40, 400, 400);
//            g.setColor(new Color(120, 120, 120));
//            g.setFont(new Font("隶书", Font.BOLD, 28));
//            g.drawString("自定义图片", 200, 200);
//            ImageIO.write(bufferedImage, "jpg", new File("D:/test.jpg"));
//        } finally {
//            g.dispose();//释放画笔
//        }
//        getOSSupportsStandardImageFormat();
//    }


    /**
     * 获取图片格式
     *
     * @param file 图片文件
     * @return 图片格式
     */
    public static String getImageFormatName(File file) throws IOException {
        String formatName = null;
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> imageReader = ImageIO.getImageReaders(iis);
        if (imageReader.hasNext()) {
            ImageReader reader = imageReader.next();
            formatName = reader.getFormatName();
        }
        return formatName;
    }


    /**
     * 获取某个文件的前缀路径
     * <p>
     * 不包含文件名的路径
     *
     * @param file 当前文件对象
     * @return
     * @throws IOException
     */
    public static String getFilePrefixPath(File file) throws IOException {
        String path;
        if (!file.exists()) {
            throw new IOException("file not exist!");
        }
        String fileName = file.getName();
        path = file.getPath().replace(fileName, "");
        return path;
    }

    /**
     * 获取某个文件的前缀路径
     * <p>
     * 不包含文件名的路径
     *
     * @param path 当前文件路径
     * @return 不包含文件名的路径
     * @throws Exception
     */
    public static String getFilePrefixPath(String path) throws Exception {
        if (null == path || path.isEmpty()) {
            throw new Exception("文件路径为空！");
        }
        int index = path.lastIndexOf(File.separator);
        if (index > 0) {
            path = path.substring(0, index + 1);
        }
        return path;
    }

    /**
     * 获取不包含后缀的文件路径(包含.)
     *
     * @param src
     * @return
     */
    public static String getPathWithoutSuffix(String src) {
        String path = src;
        int index = path.lastIndexOf(".");
        if (index > 0) {
            path = path.substring(0, index + 1);
        }
        return path;
    }

    /**
     * 获取系统支持的图片格式
     */
    public static void getOSSupportsStandardImageFormat() {
        System.out.println(Arrays.asList(ImageIO.getReaderFormatNames()));
        System.out.println(Arrays.asList(ImageIO.getReaderFileSuffixes()));
        System.out.println(Arrays.asList(ImageIO.getReaderMIMETypes()));

        String[] writerFormatName = ImageIO.getWriterFormatNames();
        String[] writerSuffixName = ImageIO.getWriterFileSuffixes();
        String[] writerMIMEType = ImageIO.getWriterMIMETypes();
    }

    public static void main(String[] args) throws IOException {
        zoom("D:/testImage.jpg", "D:/testImage2.jpg", 100, 100);
    }

    /**
     * 剪切图片
     *
     * @param sourcePath 待剪切图片路径
     * @param targetPath 裁剪后保存路径（默认为源路径）
     * @param x          起始横坐标
     * @param y          起始纵坐标
     * @param width      剪切宽度
     * @param height     剪切高度
     * @throws IOException
     * @returns 裁剪后保存路径（图片后缀根据图片本身类型生成）
     */
    public static String cutImage(String sourcePath, String targetPath, int x, int y, int width, int height) throws IOException {
        File file = new File(sourcePath);
        if (!file.exists()) {
            throw new IOException("not found the image：" + sourcePath);
        }
        if (null == targetPath || targetPath.isEmpty()) {
            targetPath = sourcePath;
        }

        String formatName = getImageFormatName(file);
        if (null == formatName) {
            return targetPath;
        }
        formatName = formatName.toLowerCase();

        // 防止图片后缀与图片本身类型不一致的情况
        String pathPrefix = getPathWithoutSuffix(targetPath);
        targetPath = pathPrefix + formatName;

        // GIF需要特殊处理
        if (IMAGE_FORMAT.GIF.getValue() == formatName) {
            GifDecoder decoder = new GifDecoder();
            int status = decoder.read(sourcePath);
            if (status != GifDecoder.STATUS_OK) {
                throw new IOException("read image " + sourcePath + " error!");
            }

            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            encoder.start(targetPath);
            encoder.setRepeat(decoder.getLoopCount());
            for (int i = 0; i < decoder.getFrameCount(); i++) {
                encoder.setDelay(decoder.getDelay(i));
                BufferedImage childImage = decoder.getFrame(i);
                BufferedImage image = childImage.getSubimage(x, y, width, height);
                encoder.addFrame(image);
            }
            encoder.finish();
        } else {
            BufferedImage image = ImageIO.read(file);
            image = image.getSubimage(x, y, width, height);
            ImageIO.write(image, formatName, new File(targetPath));
        }
        return targetPath;
    }

    /**
     * 压缩图片
     *
     * @param sourcePath 待压缩的图片路径
     * @param targetPath 压缩后图片路径（默认为初始路径）
     * @param width      压缩宽度
     * @param height     压缩高度
     * @throws IOException
     * @returns 裁剪后保存路径（图片后缀根据图片本身类型生成）
     */
    public static String zoom(String sourcePath, String targetPath, int width, int height) throws IOException {
        File file = new File(sourcePath);
        if (!file.exists()) {
            throw new IOException("not found the image ：" + sourcePath);
        }
        if (null == targetPath || targetPath.isEmpty()) {
            targetPath = sourcePath;
        }
        String formatName = getImageFormatName(file);
        if (null == formatName) {
            return targetPath;
        }
        formatName = formatName.toLowerCase();
        String pathPrefix = getPathWithoutSuffix(targetPath);
        targetPath = pathPrefix + formatName;

        // GIF处理
        if (IMAGE_FORMAT.GIF.getValue() == formatName) {
            GifDecoder decoder = new GifDecoder();
            int status = decoder.read(sourcePath);
            if (status != GifDecoder.STATUS_OK) {
                throw new IOException("read image " + sourcePath + " error!");
            }
            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            encoder.start(targetPath);
            encoder.setRepeat(decoder.getLoopCount());
            for (int i = 0; i < decoder.getFrameCount(); i++) {
                encoder.setDelay(decoder.getDelay(i));
                BufferedImage image = zoom(decoder.getFrame(i), width, height);
                encoder.addFrame(image);
            }
            encoder.finish();
        } else {
            BufferedImage image = ImageIO.read(file);
            BufferedImage zoomImage = zoom(image, width, height);
            ImageIO.write(zoomImage, formatName, new File(targetPath));
        }
        return targetPath;
    }

    /**
     * 读取图片
     *
     * @param file 图片文件
     * @return 图片数据
     * @throws IOException
     */
    private static BufferedImage[] readerImage(File file) throws IOException {
        BufferedImage sourceImage = ImageIO.read(file);
        BufferedImage[] images = null;
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
        if (imageReaders.hasNext()) {
            ImageReader reader = imageReaders.next();
            reader.setInput(iis);
            int imageNumber = reader.getNumImages(true);
            images = new BufferedImage[imageNumber];
            for (int i = 0; i < imageNumber; i++) {
                BufferedImage image = reader.read(i);
                if (sourceImage.getWidth() > image.getWidth() || sourceImage.getHeight() > image.getHeight()) {
                    image = zoom(image, sourceImage.getWidth(), sourceImage.getHeight());
                }
                images[i] = image;
            }
            reader.dispose();
            iis.close();
        }
        return images;
    }

    /**
     * 根据要求处理图片
     *
     * @param images 图片数组
     * @param x      横向起始位置
     * @param y      纵向起始位置
     * @param width  宽度
     * @param height 宽度
     * @return 处理后的图片数组
     * @throws Exception
     */
    private static BufferedImage[] processImage(BufferedImage[] images, int x, int y, int width, int height) throws Exception {
        if (null == images) {
            return images;
        }
        BufferedImage[] oldImages = images;
        images = new BufferedImage[images.length];
        for (int i = 0; i < oldImages.length; i++) {
            BufferedImage image = oldImages[i];
            images[i] = image.getSubimage(x, y, width, height);
        }
        return images;
    }

    /**
     * 写入处理后的图片到file
     * <p>
     * 图片后缀根据图片格式生成
     *
     * @param images     处理后的图片数据
     * @param formatName 图片格式
     * @param file       写入文件对象
     * @throws Exception
     */
    private static void writerImage(BufferedImage[] images, String formatName, File file) throws Exception {
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName(formatName);
        if (imageWriters.hasNext()) {
            ImageWriter writer = imageWriters.next();
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            if (index > 0) {
                fileName = fileName.substring(0, index + 1) + formatName;
            }
            String pathPrefix = getFilePrefixPath(file.getPath());
            File outFile = new File(pathPrefix + fileName);
            ImageOutputStream ios = ImageIO.createImageOutputStream(outFile);
            writer.setOutput(ios);

            if (writer.canWriteSequence()) {
                writer.prepareWriteSequence(null);
                for (int i = 0; i < images.length; i++) {
                    BufferedImage childImage = images[i];
                    IIOImage image = new IIOImage(childImage, null, null);
                    writer.writeToSequence(image, null);
                }
                writer.endWriteSequence();
            } else {
                for (int i = 0; i < images.length; i++) {
                    writer.write(images[i]);
                }
            }

            writer.dispose();
            ios.close();
        }
    }

    /**
     * 剪切格式图片
     * <p>
     * 基于JDK Image I/O解决方案
     *
     * @param sourceFile 待剪切图片文件对象
     * @param destFile   裁剪后保存文件对象
     * @param x          剪切横向起始位置
     * @param y          剪切纵向起始位置
     * @param width      剪切宽度
     * @param height     剪切宽度
     * @throws Exception
     */
    public static void cutImage(File sourceFile, File destFile, int x, int y, int width, int height) throws Exception {
        // 读取图片信息
        BufferedImage[] images = readerImage(sourceFile);
        // 处理图片
        images = processImage(images, x, y, width, height);
        // 获取文件后缀
        String formatName = getImageFormatName(sourceFile);
        destFile = new File(getPathWithoutSuffix(destFile.getPath()) + formatName);

        // 写入处理后的图片到文件
        writerImage(images, formatName, destFile);
    }

    /**
     * 压缩图片
     *
     * @param sourceImage 待压缩图片
     * @param width       压缩图片高度
     * @param height      压缩图片宽度
     */
    public static BufferedImage zoom(BufferedImage sourceImage, int width, int height) {
        Image image = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage zoomImage = new BufferedImage(width, height, sourceImage.getType());
        Graphics gc = zoomImage.getGraphics();
        gc.setColor(Color.WHITE);
        gc.drawImage(image, 0, 0, null);
        return zoomImage;
    }

    /**
     * @param args
     * @throws Exception
     */
/*    public static void main(String[] args) throws Exception {

        try {
            String filePath = "D:/testgif33.gif";
            String prefixPath = "D:/testyasuo/1.gif";
            String destPath;
            File ccFile = new File(filePath);
            if (!ccFile.exists()) {
                throw new IOException("not found the image ：" + filePath);
            }
            int loopCount = 500;
            long start = System.currentTimeMillis();

//            for (int i = 0; i < loopCount; i++) {
//                destPath = prefixPath + "CC_des_" + i + ".gif";
                zoom(filePath, prefixPath, 300, 600);
//            }
            long end = System.currentTimeMillis();
            System.out.println("开始于" + start + ", 结束于" + end + ", 平均用时" + (end - start) / loopCount + "毫秒.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 绘制图片
     *
     * @param bufferedImage 缓冲图片
     * @param type          图片类型
     * @param file          文件
     * @throws IOException
     */
    public static boolean drawSimpleImage(BufferedImage bufferedImage, String type, File file) throws IOException {
        Graphics g = bufferedImage.getGraphics();//获取图片画笔
        try {
            int backgroundX = 10;//背景x坐标
            int backgroundY = 40;//背景y坐标
            int backgroundWith = 180;//背景宽
            int backgroundHeight = 120;//背景高
            g.fillRect(backgroundX, backgroundY, backgroundWith, backgroundHeight);//填充背景，默认白色

            g.setColor(new Color(120, 120, 120));//设置画笔颜色

            int fontSize = 28;//字体大小
            g.setFont(new Font("宋体", Font.BOLD, fontSize));//设置字体

            int stringX = 10;//文字x坐标
            int stringY = 100;//文字y坐标
            g.drawString("绘制简单图片", stringX, stringY);
            return ImageIO.write(bufferedImage, type, file);
        } finally {
            g.dispose();//释放画笔
        }
    }

    private static void waterMark(Image srcImg, String path) throws IOException {
        int srcImgWidth = srcImg.getWidth(null);
        int srcImgHeight = srcImg.getHeight(null);
/*
        //网络图片
        URL url = new URL("url");
        //将URL对象输入流转化为图片对象 (url.openStream()方法，获得一个输入流)
        Image srcImg = ImageIO.read(url.openStream());
        //获取图片的宽
        int srcImgWidth = srcImg.getWidth(null);
        //获取图片的高
        int srcImgHeight = srcImg.getHeight(null);
        System.out.println("图片的宽:"+srcImgWidth);
        System.out.println("图片的高:"+srcImgHeight);
*/
        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        // 加水印
        //创建画笔
        Graphics2D g = bufImg.createGraphics();
        //绘制原始图片
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
/*
        //文字水印
        //根据图片的背景设置水印颜色
        g.setColor(new Color(255, 255, 255, 128));
        //设置字体  画笔字体样式为微软雅黑，加粗，文字大小为60pt
        g.setFont(new Font("微软雅黑", Font.BOLD, 60));
        String waterMarkContent = "自定义水印";
        //设置水印的坐标(为原图片中间位置)
        int x = srcImgWidth / 2;
        int y = srcImgHeight / 2;
        //画出水印 第一个参数是水印内容，第二个参数是x轴坐标，第三个参数是y轴坐标
        g.drawString(waterMarkContent, x, y);
        g.dispose();*/

        //图片水印
        // 水印文件
        String waterMarkImage = "D:/print.jpg";
        Image srcWaterMark = ImageIO.read(new File(waterMarkImage));
        //获取水印图片的宽度
        int widthWaterMark = srcWaterMark.getWidth(null);
        //获取水印图片的高度
        int heightWaterMark = srcWaterMark.getHeight(null);
        //设置 alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.9f));
        //绘制水印图片
        g.drawImage(srcWaterMark, (srcImgWidth - widthWaterMark) / 10,
                (srcImgHeight - heightWaterMark) / 10, widthWaterMark, heightWaterMark, null);
        // 水印文件结束
        g.dispose();

        //文件输出地址
        String tarImgPath = path;
        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
        ImageIO.write(bufImg, "png", outImgStream);
        outImgStream.flush();
        outImgStream.close();
    }


//    @SneakyThrows
//    public static void main(String[] args) {
//        //指定大小进行缩放
//        Thumbnails.of("D:/test.jpg").size(100, 100).toFile("D:/test.jpg.jpg");
//
//        //按照比例进行缩放
//        // scale 图片的压缩比例 值在0-1之间，1f就是原图，0.5就是原图的一半大小
//        // outputQuality 图片压缩的质量 值在0-1 之间，越接近1质量越好，越接近0 质量越差
//        Thumbnails.of("D:/test.jpg").scale(0.75f).outputQuality(0.8f).toFile("D:/test.jpg");
//
//        //不按照比例，指定大小进行缩放 100 keepAspectRatio(false) 默认是按照比例缩放的
//        Thumbnails.of("D:/test.jpg").size(100, 100).keepAspectRatio(false).toFile("D:/test.jpg");
//
//        //旋转  rotate(角度),正数：顺时针 负数：逆时针
//        Thumbnails.of("D:/test.jpg").size(1024, 1024).rotate(90).toFile("C:/image+90.jpg");
//
//        //水印 watermark(位置，水印图，透明度)
//        Thumbnails.of("D:/test.jpg").size(1024, 1024)
//                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("水印地址")), 0.5f)
//                .outputQuality(0.4f).toFile("输出地址");
//
//        //裁剪
//        Thumbnails.of("D:/test.jpg").sourceRegion(Positions.CENTER, 400, 400).size(200, 200).keepAspectRatio(false)
//                .toFile("输出地址");
//
//        //转化图片格式
//        Thumbnails.of("D:/test.jpg").size(666, 666).outputFormat("png").toFile("D:/test.png");
//
//        // 输出到OutputStream
//        OutputStream os = new FileOutputStream("D:/test.jpg");
//        Thumbnails.of("test.jpg").size(666, 666).toOutputStream(os);
//
//        //输出到BufferedImage
//        BufferedImage thumbnail = Thumbnails.of("D:/test.jpg").size(666, 666).asBufferedImage();
//        ImageIO.write(thumbnail, "jpg", new File("test.jpg"));
//    }
}

