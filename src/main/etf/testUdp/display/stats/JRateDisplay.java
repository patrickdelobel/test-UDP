package etf.testUdp.display.stats;

import etf.testUdp.shared.Parameters;
import etf.testUdp.stat.Stat;
import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.RadialBargraph;
import eu.hansolo.steelseries.tools.Model;

import javax.swing.*;

/**
 * Created by patrick on 09/05/17.
 */
public class JRateDisplay extends StatDisplayItem {
    protected AbstractGauge rateGauge;


    public JRateDisplay(long displayEveryMs, Stat stat, String uniqueName) {
        super(displayEveryMs, stat,uniqueName);
    }

    @Override
    public JComponent createChart() {
        Model model = new Model();
        model.setRange(0, 100);
        rateGauge = new RadialBargraph(model);
        rateGauge.setUnitString("Mb/s");
        rateGauge.setTitle(uniqueName);
        return rateGauge;
    }

    @Override
    public void displayNextData() {
        SwingUtilities.invokeLater(() ->
                rateGauge.setValue(((1000000f / stat.getMavg()) * Parameters.getBufSize()) / (1024f * 1024f)));
    }
}
