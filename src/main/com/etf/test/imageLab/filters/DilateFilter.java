package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import com.etf.test.swingUtils.JSliderWithNameAndValue;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.util.LinkedList;


/**
 * Created by patrick on 15/05/17.
 */
@Category("filter")
public class DilateFilter extends AbstractFilter {
    JSliderWithNameAndValue iterations;

    public DilateFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "dilate", mainList, mousePopupListener);

        iterations = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 20, 1, 5, "iterations");
        outputMat = new Mat();
    }

    public void run() {
        Imgproc.dilate(getPrevious().getOutputMat(), outputMat, new Mat(), new Point(-1,-1), iterations.getValue());
    }
}
