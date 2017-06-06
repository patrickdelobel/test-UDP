package com.etf.test.testUdp.display.stats;

import com.etf.test.testUdp.stat.Stat;

import javax.swing.*;

/**
 * Created by patrick on 09/05/17.
 */
public abstract class StatDisplayItem {
    protected Stat stat;
    protected String uniqueName;

    public StatDisplayItem(Stat stat, String uniqueName) {
        this.stat = stat;
        this.uniqueName = uniqueName;
    }

    public abstract JComponent createChart();

    public abstract void displayNextData();

    public Stat getStat() {
        return stat;
    }
}
