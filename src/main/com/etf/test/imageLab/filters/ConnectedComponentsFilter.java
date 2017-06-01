package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import com.etf.test.swingUtils.JSliderWithNameAndValue;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;

import static org.opencv.core.CvType.*;
import static org.opencv.imgproc.Imgproc.*;


/**
 * Created by patrick on 15/05/17.
 */
@Category("contour")
public class ConnectedComponentsFilter extends AbstractFilter {
    JSliderWithNameAndValue bwThreshold;
    JSliderWithNameAndValue statMin;

    private Region[] regions = null;
    private Mat labeled = null;

    public ConnectedComponentsFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput, LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "Connected Components", mainList, mousePopupListener);

        statMin = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 3000, 100, 500, "keep areas above this");
        bwThreshold = JSliderWithNameAndValue.addNewSliderToPanel(commandPanel, 0, 255, 128, 100, "B/W threshold");
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
        Mat bw = Mat.zeros(getPrevious().getOutputMat().size(), getPrevious().getOutputMat().type());
        Imgproc.threshold(grayImage, bw, bwThreshold.getValue(), 255, Imgproc.THRESH_BINARY_INV);

        createRegions(bw);

        // Free memory
        grayImage.release();
        bw.release();
    }

    private void createRegions(Mat image) {
        labeled = new Mat(image.size(), image.type());
//        outputMat = Mat.zeros(getPrevious().getOutputMat().size(), CV_8UC3);
        cvtColor(getPrevious().getOutputMat(),outputMat,COLOR_GRAY2RGB);

        // Extract components
        Mat rectComponents = Mat.zeros(new Size(0, 0), 0);
        Mat centComponents = Mat.zeros(new Size(0, 0), 0);
        Imgproc.connectedComponentsWithStats(image, labeled, rectComponents, centComponents);

        // Collect regions info
        int[] rectangleInfo = new int[5];
        double[] centroidInfo = new double[2];
        regions = new Region[rectComponents.rows() - 1];

        for (int i = 1; i < rectComponents.rows(); i++) {

            // Extract bounding box
            rectComponents.row(i).get(0, 0, rectangleInfo);
            Rect rectangle = new Rect(rectangleInfo[0], rectangleInfo[1], rectangleInfo[2], rectangleInfo[3]);

            if (rectangle.area() >= statMin.getValue()) {
                // Extract centroids
                centComponents.row(i).get(0, 0, centroidInfo);
                Point centroid = new Point(centroidInfo[0], centroidInfo[1]);

                regions[i - 1] = new Region(rectangle, centroid);

                //draw mass center and bounding rectangle
                line(outputMat,centroid,centroid,new Scalar(0,0,255),2);
                rectangle(outputMat, regions[i - 1].bounding.br(), regions[i - 1].bounding.tl(), new Scalar(Math.random() * 256, Math.random() * 256, Math.random() * 256));
            }
        }

        // Free memory
        rectComponents.release();
        centComponents.release();
    }

    /**
     * Extract region mask from labeled Mat
     *
     * @param region
     * @return Mat
     */
    public Mat getRegionMask(Region region) {
        int i = Arrays.asList(regions).indexOf(region);
        Mat mask = new Mat(labeled.size(), labeled.type());
        Scalar color = new Scalar(i + 1, i + 1, i + 1);
        Core.inRange(labeled, color, color, mask);
        return mask;
    }

    public Region[] getRegions() {
        return regions;
    }

    public Region getRegion(int index) {
        return regions[index];
    }

    /**
     * Call this method to release private Mat member
     */
    public void release() {
        labeled.release();
    }

    /**
     * Extract original image of the region using the region mask
     *
     * @param image Original image
     */
//    public Mat getRegionImageWithMask(Mat image, Region region) {
//        Mat mask = getMask(region);
//        Mat result = Mat.zeros(image.size(), image.type());
//        result.setTo(new Scalar(255, 255, 255));
//        image.copyTo(result, mask);
//        Mat boxImage = new Mat(result, region.getBounding());
//        mask.release();
//        result.release();
//        return boxImage;
//    }

    /**
     * Extract original image of the region using the region mask
     *
     * @param image Original image
     */
    public Mat getRegionImage(Mat image, Region region) {
        return new Mat(image, region.getBounding());
    }

    public class Region {
        private Rect bounding;
        private Point centroid;

        public Region(Rect bounding, Point centroid) {
            this.bounding = bounding;
            this.centroid = centroid;
        }

        public Rect getBounding() {
            return bounding;
        }

        public Point getCentroid() {
            return centroid;
        }
    }
}
