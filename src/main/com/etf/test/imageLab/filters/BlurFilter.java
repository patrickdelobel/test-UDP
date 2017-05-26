package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import javax.swing.*;
import java.util.LinkedList;

import static org.opencv.imgproc.Imgproc.*;


/**
 * Created by patrick on 15/05/17.
 */
public class BlurFilter extends AbstractFilter {
    JSliderWithNameAndValue ksize;

    public BlurFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "blur", mainList, mousePopupListener);

        //commandPanel is created already, add other parameters or commands
        ksize = new JSliderWithNameAndValue(1, 50, 3, "kernel size");
        ksize.setMajorTickSpacing(10);
//        delay.setMinorTickSpacing(1);
        ksize.setPaintTicks(true);
        ksize.setPaintLabels(true);
        commandPanel.add(ksize, "w 55%");

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
        blur(grayImage, outputMat, new Size(ksize.getValue(), ksize.getValue()));
    }
}
