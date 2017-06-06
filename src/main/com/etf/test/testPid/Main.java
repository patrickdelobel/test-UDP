package com.etf.test.testPid;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static SharedData sharedData = new SharedData();
    public static JFrame topFrame;
    public static MainPanel mainPanel;

    public static void main(String[] args) {

        topFrame = new JFrame("PID test");
        topFrame.setMinimumSize(new Dimension(1200, 300));
        topFrame.setBounds(1, 1, 1200, 600);
//        JButton redisplay = new JButton("redisplay");
        mainPanel = new MainPanel();
//        mainPanel.getPanel().add(redisplay);
//        redisplay.addActionListener(e -> {
//            SwingUtilities.invokeLater(() -> {
//                topFrame.getContentPane().removeAll();
//                mainPanel = new MainPanel();
//                mainPanel.getPanel().add(redisplay);
//                topFrame.getContentPane().add(mainPanel.getPanel());
//                topFrame.pack();
//            });
//        });
        topFrame.getContentPane().add(mainPanel.getPanel());
        topFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        topFrame.setVisible(true);
    }

    public static JFrame getTopFrame() {
        return topFrame;
    }
}
