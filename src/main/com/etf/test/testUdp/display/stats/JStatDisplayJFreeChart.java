package com.etf.test.testUdp.display.stats;

import com.etf.test.testUdp.Main;
import com.etf.test.testUdp.stat.Stat;
import org.apache.commons.lang3.time.DateUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by patrick on 08/05/17.
 */
public class JStatDisplayJFreeChart extends JPanel {
    private static final String TITLE = "Sent data";
    private static final int COUNT = 2 * 60;
    private Runnable timer;

    public JStatDisplayJFreeChart() {

        final DynamicTimeSeriesCollection dataset = new DynamicTimeSeriesCollection(3, COUNT, new Second());
        Date targetTime = new Date(); //now
        targetTime = DateUtils.addSeconds(targetTime, -COUNT-1); //start of graph is current time, data will be added at the end
        dataset.setTimeBase(new Second(targetTime));
        dataset.addSeries(new float[COUNT], 0, "Min");
        dataset.addSeries(new float[COUNT], 1, "Max");
        dataset.addSeries(new float[COUNT], 2, "MAvg");
        JFreeChart chart = createChart(dataset);

        this.add(new ChartPanel(chart), BorderLayout.CENTER);


        java.util.Timer timer = new java.util.Timer("statDisplayThread", true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Stat recStat = Main.sharedData.getReceiveStat();
                Stat sendStat = Main.sharedData.getSendStat();
                if (sendStat.getCount() > 0) {

                    float[] newData = new float[3];

                    newData[0] = sendStat.getMin() / 1000f;
                    newData[1] = sendStat.getMax() / 1000f;
                    newData[2] = (float) sendStat.getMavg() / 1000f;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            dataset.advanceTime();
                            dataset.appendData(newData);
                        }
                    });
                }
            }
        };

        // Every 1000 milliseconds a new value is collected.
        timer.schedule(task, 1000, 1000);

    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                TITLE, "hh:mm:ss", "milliSeconds", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        return result;
    }
}

