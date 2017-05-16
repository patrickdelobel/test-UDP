package com.etf.test.imageLab;

import com.etf.test.testUdp.display.MainPanel;
import com.etf.test.testUdp.shared.SharedData;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static SharedData sharedData = new SharedData();

    public static void main(String[] args) {

        //Thread.currentThread().setPriority(-1);//just below normal

        JFrame topFrame = new JFrame("Test jitter");
        topFrame.setMinimumSize(new Dimension(1200, 800));
        topFrame.getContentPane().add(new ImageLabMainPanel());
        topFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        topFrame.pack();
        topFrame.setVisible(true);
    }
}
