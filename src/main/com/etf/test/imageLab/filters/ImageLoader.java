package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;

import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;

/**
 * Created by patrick on 15/05/17.
 */
public class ImageLoader extends AbstractFilter {
    protected String fileName;
    JSliderWithNameAndValue desiredWidth;

    public ImageLoader(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "open", mainList, mousePopupListener);

        //default picture
        fileName = "target/classes/IMG_5431.jpg";

        //commandPanel is created already, add other parameters or commands
        JButton fileOpen = new JButton("open");
        fileOpen.addActionListener(ae -> {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("target/classes"));
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                fileName = file.getAbsolutePath();
                displayInput();
                run();
                displayOutput();
                desiredWidth.setMaximum(outputMat.width());
            }
        });
        commandPanel.add(fileOpen);

        desiredWidth = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 20, 1200, 320, 250, "desired width (px)");
    }

    public void run() {
        outputMat = Imgcodecs.imread(fileName, CV_LOAD_IMAGE_GRAYSCALE);
        double ow = outputMat.width();
        double oh = outputMat.height();
        double ratio = ow / oh;
        Imgproc.resize(outputMat, outputMat, new Size(desiredWidth.getValue(), desiredWidth.getValue() / ratio), 0, 0, Imgproc.INTER_LINEAR);

        try {
            Thread.sleep(100);//10i/s in continuous mode
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    public void displayInput() {
        emptyInputDisplay();

        if (outputMat == null || outputMat.empty())
            return;

        panelImageInput.add(matToScrollablePanel(outputMat), BorderLayout.CENTER);
        panelImageInput.updateUI();
    }
}
