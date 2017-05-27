package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import org.opencv.core.Mat;
import org.opencv.photo.Photo;

import javax.swing.*;
import java.util.LinkedList;

import static org.opencv.imgproc.Imgproc.COLOR_RGB2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;


/**
 * Created by patrick on 15/05/17.
 */
public class NlMeansDenoisingFilter extends AbstractFilter {
    JSliderWithNameAndValue templateWindowSize;
    JSliderWithNameAndValue searchWindowsSize;
    JSliderWithNameAndValue weightDecay;

    public NlMeansDenoisingFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "NL means denoising", mainList, mousePopupListener);

        weightDecay = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 1, 100, 30, 20, "weight decay (/10)");
        templateWindowSize = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 1, 50, 7, 10, "template Window Size");
        searchWindowsSize = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 1, 50, 21, 10, "search Window Size");
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
        Photo.fastNlMeansDenoising(grayImage, outputMat, weightDecay.getValue() / 10f, templateWindowSize.getValue(), searchWindowsSize.getValue());
    }
}
