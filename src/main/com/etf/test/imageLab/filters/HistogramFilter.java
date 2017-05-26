package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import org.opencv.core.Mat;

import javax.swing.*;

import java.util.LinkedList;

import static org.opencv.imgproc.Imgproc.*;


/**
 * Created by patrick on 15/05/17.
 */
public class HistogramFilter extends AbstractFilter {
    public HistogramFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "histogram", mainList, mousePopupListener);

        outputMat = new Mat();
    }

    public void run() {
        Mat grayImage = new Mat();
        AbstractFilter previous = getPrevious();
        if (previous.getOutputMat().channels() == 1) {//already in grey, just duplicate
            grayImage = previous.getOutputMat().clone();
        } else {
            cvtColor(previous.getOutputMat(), grayImage, COLOR_RGB2GRAY);
        }
        equalizeHist(grayImage, outputMat);
    }
}
