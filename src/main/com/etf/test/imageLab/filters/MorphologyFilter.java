package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import com.etf.test.swingUtils.JSliderWithNameAndValue;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * Created by patrick on 15/05/17.
 */
@Category("filter")
public class MorphologyFilter extends AbstractFilter {
    JSliderWithNameAndValue iterations;
    JSliderWithNameAndValue operation;

    public MorphologyFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "morphology", mainList, mousePopupListener);

        iterations = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 20, 1, 5, "iterations");
        operation = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 6, 1, 1, "operation");
        setTickLabels(operation,
                Arrays.asList("ERODE", 0,
                        "DILATE", 1,
                        "OPEN", 2,
                        "CLOSE", 3,
                        "GRADIENT", 4,
                        "TOPHAT", 5,
                        "BLACKHAT", 6,
                        "HITMISS", 7
                )
        );
        outputMat = new Mat();
    }

    public void run() {
        Imgproc.morphologyEx(getPrevious().getOutputMat(), outputMat, operation.getValue(), new Mat(), new Point(-1, -1), iterations.getValue());
    }
}
