package com.etf.test.imageLab.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;

/**
 * Created by patrick on 15/05/17.
 */
public class CannyFilter extends AbstractFilter {
    public CannyFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, AbstractFilter previous) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "canny", previous);

        outputMat = new Mat();
    }

    public void run() {
        Mat canny = new Mat();
        outputMat = previous.getOutputMat().clone();
        Imgproc.Canny(previous.getOutputMat(), outputMat, 10, 50, 5, false);
    }
}
