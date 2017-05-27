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
public class AdaptiveThresholdFilter extends AbstractFilter {
    JSliderWithNameAndValue adaptiveMethod;
    JSliderWithNameAndValue thresholdType;
    JSliderWithNameAndValue blockSize;
    JSliderWithNameAndValue constant;

    public AdaptiveThresholdFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "adaptive threshold", mainList, mousePopupListener);

        adaptiveMethod = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 1, 1, 1, "adaptiveMethod");
        thresholdType = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 1, 1, 1, "thresholdType");
        blockSize = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 1, 50, 5, 10, "blockSize (*2+1)");
        constant = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 1, 100, 15, 10, "constant (/10)");

        setTickLabels(thresholdType,
                Arrays.asList("ERODE", 0,
                        "DILATE", 1,
                        "OPEN", 2,
                        "CLOSE", 3,
                        "GRADIENT", 4,
                        "TOPHAT", 5,
                        "BLACKHAT", 6
                )
        );
        outputMat = new Mat();
    }

    public void run() {
        Imgproc.adaptiveThreshold(getPrevious().getOutputMat(), outputMat, 255d, adaptiveMethod.getValue(), thresholdType.getValue(),
                blockSize.getValue() * 2 + 1, constant.getValue() / 10d);
    }
}
