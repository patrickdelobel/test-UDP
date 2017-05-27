package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgproc.Imgproc.*;


/**
 * Created by patrick on 15/05/17.
 */
public class FindContourFilter extends AbstractFilter {
//    int thresh = 100;
//    int max_thresh = 255;

    public FindContourFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "contour", mainList, mousePopupListener);

    }

    public void run() {
        outputMat = new Mat(mainList.get(0).getOutputMat().size(), CV_8UC3);//size of the input
        Mat grayImage = new Mat();
        AbstractFilter previous = getPrevious();

        if (previous.getOutputMat().channels() == 1) {//already in grey, just duplicate
            grayImage = previous.getOutputMat().clone();
        } else {
            cvtColor(previous.getOutputMat(), grayImage, COLOR_RGB2GRAY);
        }

        ArrayList<MatOfPoint> contours = new ArrayList<>();
//        ArrayList<MatOfPoint> hierarchy=new ArrayList<>();
        Mat hierarchy = new Mat();

        findContours(grayImage, contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);
        //Mat drawing = new Mat(previous.getOutputMat().size(), CV_8UC3);
        outputMat.setTo(new Scalar(0, 0, 0));
        for (int i = 0; i < contours.size() && i < 1000; i++) {
            Scalar color = new Scalar(Math.random() * 256, Math.random() * 256, Math.random() * 256);
            //  drawContours(drawing, contours, i, color, 2, 8, hierarchy, 0, new Point(0, 0));
            drawContours(outputMat, contours, i, color, 1, 8, hierarchy, 0, new Point(0, 0));
        }
    }
}
