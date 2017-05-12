package etf.testUdp.display.stats;

import etf.testUdp.shared.Parameters;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by patrick on 09/05/17.
 */
public class StatDisplayManager {

    protected Map<String, StatDisplayItem> displayItems = new HashMap<>(10);

    synchronized public void addDisplayItem(StatDisplayItem displayItem) {
        displayItems.put(displayItem.uniqueName, displayItem);
    }

    public StatDisplayManager() {
        java.util.Timer timer = new java.util.Timer("StatDisplayManager", true);
        TimerTask task = new TimerTask() {
            public boolean initDone = false;

            @Override
            public void run() {
                synchronized (this) {
                    if (!initDone) {
                        initDone = true;
                        Thread.currentThread().setPriority(Parameters.getDisplayStatPrioRelative());
                    }
                    //loop over all the items to display and refresh them
                    for (StatDisplayItem displayItem : displayItems.values()) {
                        if (displayItem.getStat().getCount() > 0) {
                            //TODO handle individual timings
                            displayItem.displayNextData();
                        }
                    }
                }
            }
        };

        // Every n milliseconds the stats are refreshed
        timer.schedule(task, 1000, Parameters.getDisplayStatEveryMs());
    }
}
