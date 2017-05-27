package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by patrick on 15/05/17.
 */
public class CameraLoader extends AbstractFilter {
    VideoCapture videoCapture;
    JSliderWithNameAndValue delay;

    public CameraLoader(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "camera", mainList, mousePopupListener);


        videoCapture = new VideoCapture(0);
        if (videoCapture == null)
            throw new RuntimeException("cannot create camera capture");
        if (!videoCapture.isOpened()) {
            videoCapture.release();
            videoCapture = null;
            throw new RuntimeException("cannot open camera capture");
        }
        videoCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, 320);
        videoCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 240);

        //commandPanel is created already, add other parameters or commands
        delay = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 20, 1000, 50, 100, "delay between frames (ms)");

        outputMat = new Mat();
        displayInput();
        run();
        displayOutput();
    }

    public void run() {
        videoCapture.read(outputMat);
        try {
            Thread.sleep(delay.getValue());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void displayInput() {
        emptyInputDisplay();

        if (outputMat == null || outputMat.empty())
            return;

        panelImageInput.add(matToScrollablePanel(outputMat), BorderLayout.CENTER);
        panelImageInput.updateUI();
    }

    public void release() {
        if (videoCapture != null) {
            videoCapture.release();
            videoCapture = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        release();
    }
}
