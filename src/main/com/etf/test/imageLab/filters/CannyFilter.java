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
public class CannyFilter extends AbstractFilter {
    JSliderWithNameAndValue threshold1;
    JSliderWithNameAndValue threshold2;
    JSliderWithNameAndValue apertureSize;
    JSliderWithNameAndValue l2gradient;

    public CannyFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "canny", mainList, mousePopupListener);

        threshold1 = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 255, 10, 20, "threshold1");
        threshold2 = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 255, 50, 20, "threshold2");
        apertureSize = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 20, 5, 5, "aperture Size");
        l2gradient = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 1, 0, 1, "l2 gradient");
        setTickLabels(l2gradient, Arrays.asList("false", 0, "true", 1));

        outputMat = new Mat();
    }

    public void run() {
        final AbstractFilter previous = getPrevious();
        outputMat = previous.getOutputMat().clone();
        Imgproc.Canny(previous.getOutputMat(), outputMat, 10, 50, 5, false);
    }
}
