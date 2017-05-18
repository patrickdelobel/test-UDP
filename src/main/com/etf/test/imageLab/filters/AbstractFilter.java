package com.etf.test.imageLab.filters;

//import org.bytedeco.javacpp.opencv_core;
//import org.bytedeco.javacpp.opencv_core.IplImage;
//import org.bytedeco.javacpp.opencv_core.Mat;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.Java2DFrameConverter;
//import org.bytedeco.javacv.OpenCVFrameConverter;

import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by patrick on 15/05/17.
 */
public abstract class AbstractFilter {
//    protected OpenCVFrameConverter.ToIplImage sourceConverter;
//    protected opencv_core.IplImage iplImage;
    protected Mat outputMat;

    protected AbstractFilter previous;
    protected JButton command;
    protected JButton runCommand;
    protected JPanel commandPanel;
    protected JPanel mainCommandPanel;
    protected JPanel panelImageInput;
    protected JPanel panelImageOutput;

    protected static boolean zoomMode = true;


    public JComponent getCommand() {
        return command;
    }

    public AbstractFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, String commandLabel, AbstractFilter previous) {
        this.panelImageInput = panelImageInput;
        this.panelImageOutput = panelImageOutput;
        this.mainCommandPanel = mainCommandPanel;
        this.previous = previous;

//        sourceConverter = new OpenCVFrameConverter.ToIplImage();

        //this is the command going into the "filter's" panel
        command = new JButton(commandLabel);
        command.addActionListener(e -> {
            if (mainCommandPanel.getComponents().length > 0) {
                mainCommandPanel.remove(0);
            }
            mainCommandPanel.add(commandPanel);
            displayInput();
            displayOutput();
            mainCommandPanel.updateUI();
        });

        //this is the specific panel with the parameters and commands for the specific filter
        commandPanel = new JPanel();
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

//    public IplImage getIplImage() {
//        return iplImage;
//    }

    public Mat getOutputMat() {
        return outputMat;
    }

    //------------- helpers ---------------
    public BufferedImage toBufferedImage(Mat matrix){
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( matrix.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = matrix.channels()*matrix.cols()*matrix.rows();
        byte [] buffer = new byte[bufferSize];
        matrix.get(0,0,buffer); // get all the pixels
        BufferedImage image = new BufferedImage(matrix.cols(),matrix.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;
    }

    public JComponent matToScrollablePanel(Mat mat) {
//        final Frame frame = sourceConverter.convert(mat);
//        BufferedImage image = new Java2DFrameConverter().getBufferedImage(frame);//must take a new Java2DFrameConverter everytime to avoid duplicating images
        BufferedImage image = toBufferedImage(mat);

        JPanel imagePanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (zoomMode) {
                    g.drawImage(image, 0, 0, getParent().getWidth(), getParent().getHeight(), null);
                } else {
                    g.drawImage(image, 0, 0, null);
                }
            }
        };

        //TODO remove scrollPane if zoomMode
        imagePanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        JScrollPane sp = new JScrollPane(imagePanel);
        return sp;
    }

    public static boolean toggleZoomMode() {
        zoomMode = !zoomMode;
        return zoomMode;
    }

    public static boolean isZoomMode() {
        return zoomMode;
    }

    public static void setZoomMode(boolean zoomMode) {
        HistogramFilter.zoomMode = zoomMode;
    }

}
