package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * Created by patrick on 15/05/17.
 */
public class ThresholdFilter extends AbstractFilter {
    JSliderWithNameAndValue threshold;
    JSliderWithNameAndValue maxValue;
    JSliderWithNameAndValue thresholdType;

    public ThresholdFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "threshold", mainList, mousePopupListener);

        threshold = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 255, 64, 25, "threshold");
        maxValue = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 255, 192, 25, "maxValue");
        thresholdType = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 16, 2, 1, "thresholdType");

        setTickLabels(thresholdType,
                Arrays.asList(
                        "BINARY", 0,
                        "BINARY_INV", 1,
                        "TRUNC", 2,
                        "TOZERO", 3,
                        "TOZERO_INV", 4,
                        "MASK", 7,
                        "OTSU", 8,
                        "TRIANGLE", 16
                )
        );
        outputMat = new Mat();
    }

    public void run() {
        Imgproc.threshold(getPrevious().getOutputMat(), outputMat, threshold.getValue(), maxValue.getValue(), thresholdType.getValue());
    }
}
