package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import com.etf.test.swingUtils.JSliderWithNameAndValue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;

import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgproc.Imgproc.*;


/**
 * Created by patrick on 15/05/17.
 */
@Category("histogram")
public class HistogramDisplayFilter extends AbstractFilter {
    JSliderWithNameAndValue numBins;

    public HistogramDisplayFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "show histogram", mainList, mousePopupListener);


        numBins = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 1, 256, 16, 16, "number of bins");

        outputMat = new Mat();
    }

    public void run() {
        outputMat = getPrevious().getOutputMat();
        Mat grayImage = new Mat();
        AbstractFilter previous = getPrevious();
        if (previous.getOutputMat().channels() == 1) {//already in grey, just duplicate
            grayImage = previous.getOutputMat().clone();
        } else {
            cvtColor(previous.getOutputMat(), grayImage, COLOR_RGB2GRAY);
        }

        int hbins = numBins.getValue();
        MatOfInt channels = new MatOfInt(0);
        Mat histImg = new Mat(1, hbins, CV_8UC3, new Scalar(0));
        MatOfInt histSize = new MatOfInt(hbins);
        MatOfFloat ranges = new MatOfFloat(0f, 256f);

        calcHist(Arrays.asList(grayImage), channels, new Mat(), histImg, histSize, ranges);
        Core.normalize(histImg, histImg);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int h = 0; h < hbins; h++) {
            dataset.addValue(histImg.get(h, 0)[0], "pixel count", "" + (int)Math.floor(h * (256f / hbins)) + "-" + (int)Math.ceil((h + 1) * (256f / hbins) - 1));
        }

        //remove last panel (the histogram)
        if (commandPanel.getComponent(commandPanel.getComponents().length - 1) instanceof ChartPanel)
            commandPanel.remove(commandPanel.getComponent(commandPanel.getComponents().length - 1));

        JFreeChart chart = ChartFactory.createStackedBarChart(
                "histogram",
                null,
                null,
                dataset,
                PlotOrientation.HORIZONTAL,
                true, true, false);
        commandPanel.add(new ChartPanel(chart), "span 10,grow");
        mainCommandPanel.updateUI();
    }

    @Override
    public String getClassification() {
        return "histo";
    }
}
