package com.etf.test.imageLab.filters;

import javax.swing.*;
import java.awt.*;

import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

/**
 * Created by patrick on 15/05/17.
 */
public class ImageLoader extends AbstractFilter {
    protected String fileName;

    public ImageLoader(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, AbstractFilter previous) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "open", previous);
    }

    public void run() {
        fileName = "target/classes/test1.jpg";
//        iplImage = cvLoadImage(fileName, CV_LOAD_IMAGE_UNCHANGED);
//        outputMat = imread(fileName, CV_LOAD_IMAGE_ANYCOLOR);
        outputMat = imread(fileName, CV_LOAD_IMAGE_GRAYSCALE);
    }

//    public void displayInput() {
//        super.displayInput();
//
//        panelImageInput.add(new JTextArea(fileName), BorderLayout.CENTER);
//    }
//
//    public void displayOutput() {
//        super.displayOutput();
//
//        if (outputMat == null)
//            return;
//        panelImageOutput.add(matToScrollablePanel(outputMat), BorderLayout.CENTER);
//
//        panelImageOutput.updateUI();
//    }
}
