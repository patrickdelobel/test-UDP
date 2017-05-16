package com.etf.test.testUdp.display.stats;

import com.etf.test.testUdp.stat.Stat;

import javax.swing.*;

/**
 * Created by patrick on 09/05/17.
 */
public abstract class StatDisplayItem {
    protected long displayEveryMs;
    protected Stat stat;
    protected String uniqueName;

    public StatDisplayItem(long displayEveryMs, Stat stat, String uniqueName) {
        this.displayEveryMs = displayEveryMs;
        this.stat = stat;
        this.uniqueName = uniqueName;
    }

    public abstract JComponent createChart();

    public abstract void displayNextData();

    public long getDisplayEveryMs() {
        return displayEveryMs;
    }

    public Stat getStat() {
        return stat;
    }
}
