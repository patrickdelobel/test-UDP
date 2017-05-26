package com.etf.test.imageLab;

import com.etf.test.testUdp.shared.SharedData;
import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static SharedData sharedData = new SharedData();
    public static JFrame topFrame;

    public static void main(String[] args) {

        //Thread.currentThread().setPriority(-1);//just below normal

        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        topFrame = new JFrame("Image lab");
        topFrame.setMinimumSize(new Dimension(1200, 800));
        topFrame.getContentPane().add(new ImageLabMainPanel());
        topFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        topFrame.pack();
        topFrame.setVisible(true);
    }

    public static JFrame getTopFrame() {
        return topFrame;
    }
}
