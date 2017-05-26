package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;

/**
 * Created by patrick on 15/05/17.
 */
public class ImageLoader extends AbstractFilter {
    protected String fileName;

    public ImageLoader(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "open", mainList, mousePopupListener);

        //default picture
        fileName = "target/classes/IMG_5431.jpg";

        //commandPanel is created already, add other parameters or commands
        JButton fileOpen = new JButton("open");
        fileOpen.addActionListener(ae -> {
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
//        emptyInputDisplay();
//
//        panelImageInput.add(new JTextArea(fileName), BorderLayout.CENTER);
//        panelImageInput.updateUI();
//    }
    public void displayInput() {
        emptyInputDisplay();

        if (outputMat == null || outputMat.empty())
            return;

        panelImageInput.add(matToScrollablePanel(outputMat), BorderLayout.CENTER);
        panelImageInput.updateUI();
    }
}
