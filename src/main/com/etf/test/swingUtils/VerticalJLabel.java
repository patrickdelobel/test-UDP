package com.etf.test.swingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by patrick on 27/05/17.
 */

public class VerticalJLabel extends JLabel {
    public final static int ROTATE_RIGHT = 1;

    public final static int DONT_ROTATE = 0;

    public final static int ROTATE_LEFT = -1;

    private int rotation = ROTATE_LEFT;

    private boolean painting = false;

    public VerticalJLabel() {
        super();
    }

    public VerticalJLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public VerticalJLabel(Icon image) {
        super(image);
    }

    public VerticalJLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    public VerticalJLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public VerticalJLabel(String text) {
        super(text);
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public boolean isRotated() {
        return rotation != DONT_ROTATE;
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (isRotated())
            g2d.rotate(Math.toRadians(90 * rotation));
        if (rotation == ROTATE_RIGHT)
            g2d.translate(0, -this.getWidth());
        else if (rotation == ROTATE_LEFT)
            g2d.translate(-this.getHeight(), 0);
        painting = true;

        super.paintComponent(g2d);

        painting = false;
        if (isRotated())
            g2d.rotate(-Math.toRadians(90 * rotation));
        if (rotation == ROTATE_RIGHT)
            g2d.translate(-this.getWidth(), 0);
        else if (rotation == ROTATE_LEFT)
            g2d.translate(0, -this.getHeight());
    }

    public Insets getInsets(Insets insets) {
        insets = super.getInsets(insets);
        if (painting) {
            if (rotation == ROTATE_LEFT) {
                int temp = insets.bottom;
                insets.bottom = insets.left;
                insets.left = insets.top;
                insets.top = insets.right;
                insets.right = temp;
            } else if (rotation == ROTATE_RIGHT) {
                int temp = insets.bottom;
                insets.bottom = insets.right;
                insets.right = insets.top;
                insets.top = insets.left;
                insets.left = temp;
            }
        }
        return insets;
    }

    public Insets getInsets() {
        Insets insets = super.getInsets();
        if (painting) {
            if (rotation == ROTATE_LEFT) {
                int temp = insets.bottom;
                insets.bottom = insets.left;
                insets.left = insets.top;
                insets.top = insets.right;
                insets.right = temp;
            } else if (rotation == ROTATE_RIGHT) {
                int temp = insets.bottom;
                insets.bottom = insets.right;
                insets.right = insets.top;
                insets.top = insets.left;
                insets.left = temp;
            }
        }
        return insets;
    }

    public int getWidth() {
        if ((painting) && (isRotated()))
            return super.getHeight();
        return super.getWidth();
    }

    public int getHeight() {
        if ((painting) && (isRotated()))
            return super.getWidth();
        return super.getHeight();
    }

    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        if (isRotated()) {
            int width = d.width;
            d.width = d.height;
            d.height = width;
        }
        return d;
    }

    public Dimension getMinimumSize() {
        Dimension d = super.getMinimumSize();
        if (isRotated()) {
            int width = d.width;
            d.width = d.height;
            d.height = width;
        }
        return d;
    }

    public Dimension getMaximumSize() {
        Dimension d = super.getMaximumSize();
        if (isRotated()) {
            int width = d.width;
            d.width = d.height + 10;
            d.height = width + 10;
        }
        return d;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());
        VerticalJLabel label = new VerticalJLabel("Testing something");
        VerticalJLabel label2 = new VerticalJLabel("Testing something");
        VerticalJLabel label3 = new VerticalJLabel("Testing something");
        label.setIcon(new ImageIcon("shortcut.png"));
        label2.setIcon(new ImageIcon("shortcut.png"));
        label3.setIcon(new ImageIcon("shortcut.png"));
        label.setRotation(VerticalJLabel.ROTATE_LEFT);
        label2.setRotation(VerticalJLabel.DONT_ROTATE);
        label3.setRotation(VerticalJLabel.ROTATE_RIGHT);
        frame.getContentPane().add(label);
        frame.getContentPane().add(label2);
        frame.getContentPane().add(label3);
        frame.pack();
        frame.setVisible(true);
    }
}