package com.etf.test.testUdp.display.stats;

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
public class JStatDisplayJFreeChart2 extends StatDisplayItem {
    private static final String TITLE = "Sent data";
    private static final int COUNT = 2 * 60;
    protected DynamicTimeSeriesCollection dataset;

    public JStatDisplayJFreeChart2(long displayEveryMs, Stat stat, String uniqueName) {
        super(displayEveryMs, stat, uniqueName);
        this.dataset = dataset;
    }

    @Override
    public JComponent createChart() {
        dataset = new DynamicTimeSeriesCollection(3, COUNT, new Second());
        Date targetTime = new Date(); //now
        targetTime = DateUtils.addSeconds(targetTime, -COUNT - 1); //start of graph is current time, data will be added at the end
        dataset.setTimeBase(new Second(targetTime));
        dataset.addSeries(new float[COUNT], 0, "Min");
        dataset.addSeries(new float[COUNT], 1, "Max");
        dataset.addSeries(new float[COUNT], 2, "MAvg");
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                TITLE, "hh:mm:ss", "milliSeconds", dataset, true, true, false);
        final XYPlot plot = chart.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();

        return new ChartPanel(chart);
    }

    @Override
    public void displayNextData() {
        float[] newData = new float[3];

        newData[0] = stat.getMin() / 1000f;
        newData[1] = stat.getMax() / 1000f;
        newData[2] = (float) stat.getMavg() / 1000f;
        SwingUtilities.invokeLater(() -> {
            dataset.advanceTime();
            dataset.appendData(newData);
        });
    }
}

