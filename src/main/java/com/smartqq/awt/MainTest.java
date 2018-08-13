package com.smartqq.awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by fuqinqin on 2018/8/10.
 */
public class MainTest {
    public static void main(String[] args){
//        frame();
//        scrollPane();
//        BorderLayout();
//        counter();
//        boxLayout();
//        box();
//        test();
//        dialog();
//        fileDialog();
        closeWindow();
    }

    private static void frame(){
        Frame frame = new Frame("测试窗口");
        Panel panel = new Panel();
        panel.add(new TextField(20));
        panel.add(new Button("click me"));
        panel.add(new Button("click me"));
        panel.add(new Button("click me"));
        panel.add(new Button("click me"));
        panel.add(new Button("click me"));
        panel.add(new Button("click me"));
        panel.add(new TextField(200));
        frame.add(panel);
//        frame.setBounds(300,300, 250, 200);
        frame.pack();
        frame.setVisible(true);
    }

    private static void scrollPane(){
        Frame frame = new Frame("测试窗口");
        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        scrollPane.add(new TextField(20));
        scrollPane.add(new Button("click me"));
        frame.add(scrollPane);
        frame.setBounds(300,300, 250, 200);
        frame.setVisible(true);
    }

    private static void BorderLayout(){
        Frame f = new Frame("测试窗口");
        f.setLayout(new BorderLayout(30, 5));
        f.add(new Button("s"), BorderLayout.SOUTH);
        f.add(new Button("n"), BorderLayout.NORTH);
        Panel p = new Panel();
        p.add(new TextField(30));
        p.add(new Button("click"));
        f.add(p);
//        f.add(new Button("c"));
        f.add(new Button("e"), BorderLayout.EAST);
//        f.add(new Button("w"), BorderLayout.WEST);
        // 自动调整大小
        f.pack();
        f.setVisible(true);
    }

    /**
     * 计算器界面
     * */
    private static void counter(){
        Frame f = new Frame("计算器");
        Panel p1 = new Panel();
        p1.add(new TextField(30));
        f.add(p1, BorderLayout.NORTH);
        String[] strs = {"0","1","2","3","4","5","6","7","8","9","+","-","*","/","."};
        Panel p2 = new Panel();
        p2.setLayout(new GridLayout(3, 5, 4, 4));
        for (int i = 0; i < strs.length; i++) {
            p2.add(new Button(strs[i]));
        }
        f.add(p2);
        f.pack();
        f.setVisible(true);
    }

    private static void boxLayout(){
        Frame f = new Frame("测试窗口");
        f.setLayout(new BoxLayout(f, BoxLayout.X_AXIS));
        f.add(new Button("click_1"));
        f.add(new Button("click_2"));
        f.pack();
        f.setVisible(true);
    }

    /**
     * Box容器
     * */
    private static void box(){
        Frame f = new Frame("测试窗口");
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createVerticalBox();
        box1.add(new Button("click_1"));
        box1.add(Box.createHorizontalGlue());
        box1.add(new Button("click_2"));
        box2.add(new Button("click_a"));
        box2.add(Box.createVerticalStrut(10));
        box2.add(new Button("click_b"));
        f.add(box1, BorderLayout.NORTH);
        f.add(box2);
        f.pack();
        f.setVisible(true);
    }

    /**
     * Dialog
     * */
    private static void dialog(){
        Frame f = new Frame("测试窗口");
        final Dialog d1 = new Dialog(f, "模式窗口", true);
        final Dialog d2 = new Dialog(f, "非模式窗口", false);
        Button b1 = new Button("1");
        Button b2 = new Button("2");
        b1.addActionListener(e -> d1.setVisible(true));
        b2.addActionListener(e -> d2.setVisible(true));
        f.add(b1);
        f.add(b2, BorderLayout.SOUTH);
        f.pack();
        f.setVisible(true);
    }

    /**
     * FileDialog
     * */
    private static void fileDialog(){
        Frame f = new Frame("测试窗口");
        FileDialog d1 = new FileDialog(f, "选择需要打开文件", FileDialog.LOAD);
        FileDialog d2 = new FileDialog(f, "选择保存文件的路径", FileDialog.SAVE);
        Button b1 = new Button("open");
        Button b2 = new Button("save");
        b1.addActionListener(e -> {
            d1.setVisible(true);
            System.out.println(d1.getDirectory());
            System.out.println(d1.getFile());
        });
        b2.addActionListener(e -> {
            d2.setVisible(true);
            System.out.println(d2.getDirectory());
            System.out.println(d2.getFile());
        });
        f.add(b1);
        f.add(b2, BorderLayout.SOUTH);
        f.pack();
        f.setVisible(true);
    }

    /**
     * Close Window
     * */
    private static void closeWindow(){
        Frame f = new Frame("测试窗口");
        f.addWindowListener(new MyWindownListener());
        f.pack();
        f.setVisible(true);
    }

    public static class MyWindownListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            System.out.println("窗口马上关闭");
            System.exit(0);
        }
    }

    private static void test(){
        Frame f = new Frame("测试窗口");
        Panel p = new Panel();
        p.add(new Label("age"));
        p.add(new Scrollbar(Scrollbar.HORIZONTAL, 100, 10, 0, 100));
        f.add(p);
        f.pack();
        f.setVisible(true);
    }
}
