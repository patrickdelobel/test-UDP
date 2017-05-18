package com.etf.test.imageLab.filters;

import org.opencv.core.Mat;

import javax.swing.*;

import static org.opencv.imgproc.Imgproc.COLOR_RGB2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.equalizeHist;

//import static org.bytedeco.javacpp.opencv_core.Mat;
//import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * Created by patrick on 15/05/17.
 */
public class HistogramFilter extends AbstractFilter {
    public HistogramFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, AbstractFilter previous) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "histogram", previous);

        outputMat = new Mat();
    }

    public void run() {
        Mat grayImage = new Mat();
        if (previous.getOutputMat().channels() == 1) {//already in grey, just duplicate
            grayImage = previous.getOutputMat().clone();
        } else {
            cvtColor(previous.getOutputMat(), grayImage, COLOR_RGB2GRAY);
        }
        equalizeHist(grayImage, outputMat);
    }
}
