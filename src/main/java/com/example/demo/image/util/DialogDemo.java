package com.example.demo.image.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DialogDemo implements ActionListener{

    private JFrame frame;
    private Panel panel, panelButton, panelText;
    private JLabel labelHight, labelWidth;
    //固定缩放宽度和长度
    private JTextField textHight;
    private JTextField textWidth;
    //操作记录提示框
    private JTextArea textArea;
    //操作按钮
    private JButton buttonReduce, buttonEnlarge, buttonZoom;
    //菜单栏：打开图片、保存图片、关于、退出
    private JMenuItem itemSave, itemOpen,itemAbout, itemExit;
    //打开图片窗口，保存图片窗口
    private FileDialog dialogOpen;
    private FileDialog dialogSave;
  //BufferedImage用于保存图片
      private BufferedImage bufferedImage;
  //图片显示imageCanvas类（继承Canvas）
      private imageCanvas canvas;
      private Image image;
      private Graphics graphics;
    
        
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DialogDemo window = new DialogDemo();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //图片显示imageCanvas类（继承Canvas），用于图片重新绘制
    class imageCanvas extends Canvas
    {
         //重写Canvas的paint方法
         public void paint(Graphics g)
         {
               //将image绘制到该组件上
               g.drawImage(bufferedImage, 0, 0, null);
               //f.setVisible(true);
         }
    }
    
    //构造函数初始化图像界面
    public DialogDemo() {

        frame = new JFrame();
        frame.setBounds(100, 100, 900, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        
        canvas = new imageCanvas();
        canvas.setPreferredSize(new Dimension(800, 600));
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        
        panel = new Panel();
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        panel.setLayout(new GridLayout(1, 0, 0, 0));
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText("\u63D0\u793A\uFF1A\r\n");
        panel.add(textArea);
        
        panelButton = new Panel();
        panel.add(panelButton);
        panelButton.setLayout(new GridLayout(3, 1, 0, 0));
        
        buttonReduce = new JButton("\u56FE\u7247\u7F29\u5C0F\u4E00\u500D");
        panelButton.add(buttonReduce);buttonReduce.addActionListener(this);
        
        buttonEnlarge = new JButton("\u56FE\u7247\u653E\u5927\u4E00\u500D");
        panelButton.add(buttonEnlarge);buttonEnlarge.addActionListener(this);
        
        panelText = new Panel();
        panelButton.add(panelText);
        panelText.setLayout(new GridLayout(1, 0, 0, 0));
        
        labelHight = new JLabel("\u957F\u5EA6(px)");
        panelText.add(labelHight);
        
        textHight = new JTextField();
        panelText.add(textHight);
        textHight.setColumns(10);
        
        labelWidth = new JLabel("\u5BBD\u5EA6(px)");
        panelText.add(labelWidth);
        
        textWidth = new JTextField();
        panelText.add(textWidth);
        textWidth.setColumns(10);
        
        buttonZoom = new JButton("\u56FA\u5B9A\u7F29\u653E");
        panelText.add(buttonZoom);
        buttonZoom.addActionListener(this);
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu newMenu = new JMenu("\u6587\u4EF6\r\n");
        menuBar.add(newMenu);
        
        itemOpen = new JMenuItem("\u6253\u5F00\u56FE\u7247\r\n");
        newMenu.add(itemOpen);itemOpen.addActionListener(this);
        
        itemSave = new JMenuItem("\u4FDD\u5B58\u56FE\u7247\r\n");
        newMenu.add(itemSave);itemSave.addActionListener(this);
        
        itemAbout = new JMenuItem("\u5173\u4E8E");
        newMenu.add(itemAbout);itemAbout.addActionListener(this);
        
        JSeparator separator = new JSeparator();
        newMenu.add(separator);
        
        itemExit = new JMenuItem("\u9000\u51FA\r\n");
        newMenu.add(itemExit);itemExit.addActionListener(this);
        
        dialogOpen = new FileDialog(frame, "选择一张图片", FileDialog.LOAD);
        dialogSave = new FileDialog(frame, "选择保存图片的路径", FileDialog.SAVE);
    }

    /**
     * 界面交互，响应事件（调用对应的函数）
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemSave) {
            saveImage();
        } else if (e.getSource() == itemOpen) {
            openImage();
        } else if (e.getSource() == itemExit) {
            System.exit(0);
        } else if (e.getSource() == itemAbout) {
            JOptionPane.showMessageDialog(null, "图片缩放程序：PhotoZoomer 1.0",
                    "版本", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == buttonEnlarge) {
            enlargeImage();
        } else if (e.getSource() == buttonReduce) {
            reduceImage();
        } else if (e.getSource() == buttonZoom) {
            zoomImage();
        }
        
    }

    /**
     * 响应事件封装成函数
     */
    //打开图片
    private void openImage() {
        try {
            // 创建一个不带透明色的BufferedImage对象
             bufferedImage = new BufferedImage(1920, 890, BufferedImage.TYPE_INT_RGB);
             bufferedImage.flush();
             graphics = bufferedImage.getGraphics();
             //打开对话框
             dialogOpen.setVisible(true);
             image = ImageIO.read(new File(dialogOpen.getDirectory() + dialogOpen.getFile()));
             //判断图片是否存在
             if (image != null) {
                 graphics.drawImage(image,0,0, null);
                 canvas.repaint();
             }
             //添加提示
             textArea.append("打开图片成功！\n图片路径：" +
                     dialogOpen.getDirectory()+"\n"+"图片名称："+dialogOpen.getFile()+"\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("打开图片发生错误！");
        }
    }
    
    //保存图片
    private void saveImage() {
        try {
            dialogSave.setVisible(true);
            ImageIO.write(bufferedImage, "jpeg", 
                    new File(dialogSave.getDirectory() + dialogSave.getFile()));
            //添加提示
            textArea.append("添加图片成功！\n保存目录："+dialogSave.getDirectory()+"\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保存图片发生错误！");
        }    
    }
    
    //固定放缩图片
    private void zoomImage() {
        int height = Integer.parseInt(textHight.getText());
        int width = Integer.parseInt(textWidth.getText());
        //判断输入是否符合条件
        if (height > 0 && width > 0 && height <= 890 && width <= 1920 ) {
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            graphics = bufferedImage.getGraphics();
            graphics.drawImage(image, 0, 0, width, height, null);
            canvas.repaint();
            textArea.append("\n图片缩放为高："+height+"px，宽："+width+"px\n");
            textHight.setText("");
            textWidth.setText("");
        } else {
            textArea.append("\n请输入正确的图片宽度和长度！");
            textHight.setText("");
            textWidth.setText("");
        }
    }
    
    //放大图片一倍
    private void enlargeImage() {
        int height = image.getHeight(null) * 2;
        int width = image.getWidth(null) * 2;
        //判断输入是否符合条件
        if (height > 0 && width > 0 && height <= 890 && width <= 1920 ) {
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            graphics = bufferedImage.getGraphics();
            graphics.drawImage(image, 0, 0, width, height, null);
            canvas.repaint();
            textArea.append("\n图片缩放为高："+height+"px，宽："+width+"px\n");
            textHight.setText("");
            textWidth.setText("");
        } else {
            textArea.append("\n不能再进行放大了！");
            textHight.setText("");
            textWidth.setText("");
        }
    }
    
    //缩小图片一倍
    private void reduceImage() {
        int height = image.getHeight(null) / 2;
        int width = image.getWidth(null) / 2;
        //判断输入是否符合条件
        if (height > 0 && width > 0 && height <= 890 && width <= 1920 ) {
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            graphics = bufferedImage.getGraphics();
            graphics.drawImage(image, 0, 0, width, height, null);
            canvas.repaint();
            textArea.append("\n图片缩放为高："+height+"px，宽："+width+"px\n");
            textHight.setText("");
            textWidth.setText("");
        } else {
            textArea.append("\n不能再进行缩小了！");
            textHight.setText("");
            textWidth.setText("");
        }
    }
}