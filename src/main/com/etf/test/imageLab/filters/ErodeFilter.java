package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.util.LinkedList;


/**
 * Created by patrick on 15/05/17.
 */
public class ErodeFilter extends AbstractFilter {
    JSliderWithNameAndValue iterations;

    public ErodeFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "erode", mainList, mousePopupListener);

        iterations = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 20, 1, 5, "iterations");
        outputMat = new Mat();
    }

    public void run() {
        Imgproc.erode(getPrevious().getOutputMat(), outputMat, new Mat(), new Point(-1,-1), iterations.getValue());
    }
}
