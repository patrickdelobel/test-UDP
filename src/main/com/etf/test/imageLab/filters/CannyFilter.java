package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Created by patrick on 15/05/17.
 */
public class CannyFilter extends AbstractFilter {
    public CannyFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "canny", mainList, mousePopupListener);

        outputMat = new Mat();
    }

    public void run() {
        final AbstractFilter previous = getPrevious();
        outputMat = previous.getOutputMat().clone();
        Imgproc.Canny(previous.getOutputMat(), outputMat, 10, 50, 5, false);
    }
}
