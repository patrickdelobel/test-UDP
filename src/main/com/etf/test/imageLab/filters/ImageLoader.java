package com.etf.test.imageLab.filters;

import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

//import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
//import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;

/**
 * Created by patrick on 15/05/17.
 */
public class ImageLoader extends AbstractFilter {
    protected String fileName;

    public ImageLoader(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, AbstractFilter previous) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "open", previous);

        //default picture
        fileName = "target/classes/IMG_5431.jpg";

        //commandPanel is created already, add other parameters or commands
        JButton fileOpen = new JButton("open");
        fileOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("target/classes"));
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    fileName = file.getAbsolutePath();
                    displayInput();
                    run();
                    displayOutput();
                }
            }
        });
        commandPanel.add(fileOpen);
    }

    public void run() {
//        fileName = "target/classes/test1.jpg";
//        iplImage = cvLoadImage(fileName, CV_LOAD_IMAGE_UNCHANGED);
//        outputMat = imread(fileName, CV_LOAD_IMAGE_ANYCOLOR);
        outputMat = Imgcodecs.imread(fileName, CV_LOAD_IMAGE_GRAYSCALE);
    }

//    public void displayInput() {
//        super.displayInput();
//
//        panelImageInput.add(new JTextArea(fileName), BorderLayout.CENTER);
//    }
//
//    public void displayOutput() {
//        super.displayOutput();
//
//        if (outputMat == null)
//            return;
//        panelImageOutput.add(matToScrollablePanel(outputMat), BorderLayout.CENTER);
//
//        panelImageOutput.updateUI();
//    }
}
