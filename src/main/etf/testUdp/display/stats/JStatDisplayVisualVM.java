package etf.testUdp.display.stats;

import com.sun.tools.visualvm.charts.ChartFactory;
import com.sun.tools.visualvm.charts.SimpleXYChartDescriptor;
import com.sun.tools.visualvm.charts.SimpleXYChartSupport;
import etf.testUdp.Main;
import etf.testUdp.stat.Stat;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by patrick on 08/05/17.
 */
public class JStatDisplayVisualVM extends JPanel {

    private static final long SLEEP_TIME = 500;
    private static final int VALUES_LIMIT = 150;
    private static final int ITEMS_COUNT = 3;
    private SimpleXYChartSupport support;

    public JStatDisplayVisualVM(MigLayout migLayout) {
        super(migLayout);
        createModels();
        setLayout(new BorderLayout());
        support.setZoomingEnabled(true);
        add(support.getChart(), BorderLayout.CENTER);
    }

    private void createModels() {
        SimpleXYChartDescriptor descriptor =
                SimpleXYChartDescriptor.decimal(0, 1000, 5000, 1d, true, VALUES_LIMIT);
//                SimpleXYChartDescriptor.decimal(0,true, VALUES_LIMIT);

        descriptor.addLineFillItems("Max");
        descriptor.addLineFillItems("MAvg");
        descriptor.addLineFillItems("Min");

        descriptor.setDetailsItems(new String[]{"Detail 1", "Detail 2", "Detail 3"});
        descriptor.setChartTitle("<html><font size='+1'><b>Demo Chart</b></font></html>");
        descriptor.setXAxisDescription("<html>X Axis <i>[time]</i></html>");
        descriptor.setYAxisDescription("<html>Y Axis <i>[units]</i></html>");

        support = ChartFactory.createSimpleXYChart(descriptor);

        final Generator generator = new Generator(support);
        generator.setPriority(Thread.NORM_PRIORITY - 1);
        generator.start();
    }

    private static class Generator extends Thread {

        private SimpleXYChartSupport support;

        public void run() {
//            Runnable valuesUpdater = new Runnable() {
            while (true) {
                try {
                    long[] values = new long[3];
                    Stat recStat = Main.sharedData.getReceiveStat();
                    Stat sendStat = Main.sharedData.getSendStat();

                    if (recStat.getCount() > 1) {
                        values[0] = recStat.getMax();
                        values[1] = Math.round(recStat.getMavg());
                        values[2] = recStat.getMin();

                        if (SwingUtilities.isEventDispatchThread()) System.out.print("+");
//                        if (SwingUtilities.isEventDispatchThread()) valuesUpdater.run();
//                        else SwingUtilities.invokeLater(valuesUpdater);

                        support.addValues(System.currentTimeMillis(), values);
                    }
                    Thread.sleep(SLEEP_TIME);
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        private Generator(SimpleXYChartSupport support) {
            this.support = support;
        }
    }

}