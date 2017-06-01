package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import com.etf.test.swingUtils.JSliderWithNameAndValue;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.util.LinkedList;

import static org.opencv.core.CvType.CV_8U;
import static org.opencv.imgproc.Imgproc.COLOR_RGB2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;


/**
 * Created by patrick on 15/05/17.
 */
@Category("contour")
public class LaplacianFilter extends AbstractFilter {
    JSliderWithNameAndValue scale;
    JSliderWithNameAndValue delta;
    JSliderWithNameAndValue kernelSize;

    public LaplacianFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "Laplacian", mainList, mousePopupListener);

        kernelSize = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 10, 2, 5, "kernel size (*2+1)");
        scale = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 50, 10, 10, "scale (/10)");
        delta = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 255, 0, 30, "delta");
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
        Imgproc.Laplacian(grayImage, outputMat, CV_8U, kernelSize.getValue() * 2 + 1, scale.getValue() / 10d, delta.getValue());
    }
}
