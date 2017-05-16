package com.etf.test.testUdp.display.stats;

import com.etf.test.testUdp.Main;
import com.etf.test.testUdp.stat.Stat;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by patrick on 08/05/17.
 */
public class JStatDisplayJChart2D extends Chart2D {
    public JStatDisplayJChart2D() {
        // Create an ITrace:
        // Note that dynamic charts need limited amount of values!!!
        ITrace2D trace1 = new Trace2DLtd(200);
        ITrace2D trace2 = new Trace2DLtd(200);
        ITrace2D trace3 = new Trace2DLtd(200);
        trace1.setColor(Color.RED);
        trace2.setColor(Color.BLUE);
        trace3.setColor(Color.GREEN);

        // Add the trace to the chart. This has to be done before adding points (deadlock prevention):
        addTrace(trace1);
        addTrace(trace2);
        addTrace(trace3);

         /*
     * Now the dynamic adding of points. This is just a demo!
     *
     * Use a separate thread to simulate dynamic adding of date.
     * Note that you do not have to copy this code. Dynamic charting is just about
     * adding points to traces at runtime from another thread. Whenever you hook on
     * to a serial port or some other data source with a polling Thread (or an event
     * notification pattern) you will have your own thread that just has to add points
     * to a trace.
     */

        Timer timer = new Timer(true);
        TimerTask task = new TimerTask(){

            private double m_y = 0;
            private long m_starttime = System.currentTimeMillis();
            /**
             * @see java.util.TimerTask#run()
             */
            @Override
            public void run() {
                // This is just computation of some nice looking value.
//                double rand = Math.random();
//                boolean add = (rand >= 0.5) ? true : false;
//                this.m_y = (add) ? this.m_y + Math.random() : this.m_y - Math.random();
//                // This is the important thing: Point is added from separate Thread.
//                trace.addPoint(((double) System.currentTimeMillis() - this.m_starttime), this.m_y);

                Stat recStat = Main.sharedData.getReceiveStat();
                Stat sendStat = Main.sharedData.getSendStat();
                trace1.addPoint(((double) System.currentTimeMillis() - this.m_starttime), sendStat.getMax());
                trace2.addPoint(((double) System.currentTimeMillis() - this.m_starttime), sendStat.getMavg());
                trace3.addPoint(((double) System.currentTimeMillis() - this.m_starttime), sendStat.getMin());
            }

        };
        // Every 20 milliseconds a new value is collected.
        timer.schedule(task, 1000, 20);
    }
}
