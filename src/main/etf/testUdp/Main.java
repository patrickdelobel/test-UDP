package etf.testUdp;

import etf.testUdp.display.MainPanel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame topFrame = new JFrame("Test jitter");
        topFrame.getContentPane().add(new MainPanel().getPanel());
        topFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        topFrame.pack();
        topFrame.setVisible(true);
    }
}
