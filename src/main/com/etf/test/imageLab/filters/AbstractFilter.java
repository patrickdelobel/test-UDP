package com.etf.test.imageLab.filters;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by patrick on 15/05/17.
 */
public abstract class AbstractFilter {
    protected OpenCVFrameConverter.ToIplImage sourceConverter;
    protected opencv_core.IplImage iplImage;
    protected Mat outputMat;

    protected AbstractFilter previous;
    protected JButton command;
    protected JButton runCommand;
    protected JPanel commandPanel;
    protected JPanel mainCommandPanel;
    protected JPanel panelImageInput;
    protected JPanel panelImageOutput;

    public JComponent getCommand() {
        return command;
    }

    public AbstractFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, String commandLabel, AbstractFilter previous) {
        this.panelImageInput = panelImageInput;
        this.panelImageOutput = panelImageOutput;
        this.mainCommandPanel = mainCommandPanel;
        this.previous = previous;

        sourceConverter = new OpenCVFrameConverter.ToIplImage();

        commandPanel = new JPanel();
        command = new JButton(commandLabel);
        command.addActionListener(e -> {
            if (mainCommandPanel.getComponents().length > 0)
                mainCommandPanel.remove(0);
            mainCommandPanel.add(commandPanel);
            displayInput();
            displayOutput();
            mainCommandPanel.updateUI();
        });

        runCommand = new JButton("run filter");
        runCommand.addActionListener(e -> {
            displayInput();
            run();
            displayOutput();
        });

        commandPanel.add(runCommand);
    }

    abstract public void run();

    public void displayInput() {
        emptyInputDisplay();

        if (previous == null || previous.outputMat == null)
            return;

        panelImageInput.add(matToScrollablePanel(previous.outputMat), BorderLayout.CENTER);

        panelImageInput.updateUI();
    }

    protected void emptyInputDisplay() {
        while (panelImageInput.getComponents().length > 0) {
            panelImageInput.remove(0);
        }
    }

    public void displayOutput() {
        emptyOutputDisplay();

        if (outputMat == null || outputMat.empty())
            return;

        panelImageOutput.add(matToScrollablePanel(outputMat), BorderLayout.CENTER);
        panelImageOutput.updateUI();
    }

    protected void emptyOutputDisplay() {
        while (panelImageOutput.getComponents().length > 0) {
            panelImageOutput.remove(0);
        }
    }

    public IplImage getIplImage() {
        return iplImage;
    }

    public Mat getOutputMat() {
        return outputMat;
    }

    //------------- helpers ---------------
    public JComponent matToScrollablePanel(Mat mat) {
        final Frame frame = sourceConverter.convert(mat);
        BufferedImage image = new Java2DFrameConverter().getBufferedImage(frame);//must take a new Java2DFrameConverter everytime to avoid duplicating images

        JPanel jPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };

        jPanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        JScrollPane sp = new JScrollPane(jPanel);
        return sp;
    }
}
