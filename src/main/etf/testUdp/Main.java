package etf.testUdp;

import etf.testUdp.display.MainPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame topFrame = new JFrame("Test jitter");
        topFrame.setMinimumSize(new Dimension(800, 500));
        topFrame.setMaximumSize(new Dimension(1200, 800));
        topFrame.getContentPane().add(new MainPanel().getPanel());
        topFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        topFrame.pack();
        topFrame.setVisible(true);
    }
}
