package etf.testUdp;

import etf.testUdp.display.MainPanel;
import etf.testUdp.shared.SharedData;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static SharedData sharedData = new SharedData();

    public static void main(String[] args) {

        //Thread.currentThread().setPriority(-1);//just below normal

        JFrame topFrame = new JFrame("Test jitter");
        topFrame.setMinimumSize(new Dimension(1200, 500));
        topFrame.setMaximumSize(new Dimension(1600, 1000));
        topFrame.getContentPane().add(new MainPanel().getPanel());
        topFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        topFrame.pack();
        topFrame.setVisible(true);
    }
}
