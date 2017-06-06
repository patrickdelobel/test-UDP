package com.etf.test.testPid;

import com.etf.test.testUdp.display.stats.StatDisplayItem;
import com.etf.test.testUdp.stat.Stat;
import org.apache.commons.lang3.time.DateUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;

import javax.swing.*;
import java.util.Date;

/**
 * Created by patrick on 08/05/17.
 */
public class JSimulDisplay {
    private static final String TITLE = "Simulation truck speed";
    private static final int COUNT = 2 * 60;
    protected DynamicTimeSeriesCollection dataset;

    public JSimulDisplay() {
    }

    public JComponent createChart() {
        dataset = new DynamicTimeSeriesCollection(2, COUNT, new Second());
        Date targetTime = new Date(); //now
        targetTime = DateUtils.addSeconds(targetTime, -COUNT - 1); //start of graph is current time, data will be added at the end
        dataset.setTimeBase(new Second(targetTime));
        dataset.addSeries(new float[COUNT], 0, "Set Point");
        dataset.addSeries(new float[COUNT], 1, "Process Value");
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                TITLE, "hh:mm:ss", "speed (km/h)", dataset, true, true, false);
        final XYPlot plot = chart.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();

        return new ChartPanel(chart);
    }

    public void displayNextData(double sp, double pv) {
        float[] newData = new float[2];

        newData[0] = (float) sp;
        newData[1] = (float) pv;
        SwingUtilities.invokeLater(() -> {
            dataset.advanceTime();
            dataset.appendData(newData);
        });
    }
}

