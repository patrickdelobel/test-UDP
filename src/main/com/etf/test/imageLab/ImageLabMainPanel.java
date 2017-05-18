package com.etf.test.imageLab;

import com.etf.test.imageLab.filters.AbstractFilter;
import com.etf.test.imageLab.filters.CannyFilter;
import com.etf.test.imageLab.filters.HistogramFilter;
import com.etf.test.imageLab.filters.ImageLoader;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by patrick on 15/05/17.
 */
public class ImageLabMainPanel extends JPanel {
    JPanel panelFilters;
    JPanel panelImages;
    JPanel panelImageInput = new JPanel();
    JPanel panelImageOutput = new JPanel();
    JPanel panelParameters;

    public ImageLabMainPanel() throws HeadlessException {
        super(new MigLayout(
                "",
                "[100%]",
                "[5%:10%:20%][5%:10%:20%][30%:80%:100%]"
        ));

        //create parameters panel
        panelParameters = new JPanel();
        panelParameters.setLayout(
                new MigLayout(
                        "",
                        "[]",
                        "[]"
                ));
        panelParameters.setBorder(new BevelBorder(BevelBorder.LOWERED));

        //create filters
        ImageLoader imageLoader = new ImageLoader(panelParameters, panelImageInput, panelImageOutput, null);
        HistogramFilter histogramFilter = new HistogramFilter(panelParameters, panelImageInput, panelImageOutput, imageLoader);
        CannyFilter cannyFilter = new CannyFilter(panelParameters, panelImageInput, panelImageOutput, histogramFilter);

        //create the main panels of the application
        //filers panel
        panelFilters = new JPanel();
        panelFilters.setLayout(
                new MigLayout(
                        "",
                        "[]",
                        "[]"
                ));
        panelFilters.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panelFilters.add(imageLoader.getCommand());
        panelFilters.add(cannyFilter.getCommand());
        panelFilters.add(histogramFilter.getCommand());
        JButton runAll = new JButton("run all");
        panelFilters.add(runAll, "push, al right");
        runAll.addActionListener(e -> {
            imageLoader.displayInput();
            imageLoader.run();
            histogramFilter.run();
            cannyFilter.run();
            histogramFilter.displayOutput();
        });
        JToggleButton zoom = new JToggleButton("zoom");
        panelFilters.add(zoom, "al right");
        zoom.addActionListener(e -> {
            AbstractFilter.toggleZoomMode();
            //TODO redisplay current tool
        });

        //images panel
        panelImages = new JPanel();
        panelImages.setLayout(
                new MigLayout(
                        "",
                        "[grow][grow]",
//                        "[50%][50%]",
                        "[grow]"
                ));
        panelImages.setBorder(new BevelBorder(BevelBorder.LOWERED));
        //test
//        OpenCVFrameConverter.ToIplImage sourceConverter = new OpenCVFrameConverter.ToIplImage();
//        CanvasFrame source = new CanvasFrame("Source");
//        String fileName = "target/classes/test1.jpg";
//        opencv_core.IplImage src = cvLoadImage(fileName, 0);
//        source.hide();
//        source.showImage(sourceConverter.convert(src));

//        panelImages.add(source.getCanvas(), "growx, growy");
        panelImageInput.setBorder(new BevelBorder(BevelBorder.RAISED));
        panelImageInput.setLayout(new BorderLayout());
        panelImageOutput.setBorder(new BevelBorder(BevelBorder.RAISED));
        panelImageOutput.setLayout(new BorderLayout());

        panelImages.add(panelImageInput, "growx, growy");
        panelImages.add(panelImageOutput, "growx, growy");
//        panelImages.add(new JButton("img2"), "grow");

        add(panelFilters, "wrap, growx");
        add(panelParameters, "wrap, growx");
        add(panelImages, "growx");

    }
}
