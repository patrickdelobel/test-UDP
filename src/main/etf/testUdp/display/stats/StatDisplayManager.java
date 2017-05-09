package etf.testUdp.display.stats;

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
            @Override
            public void run() {
                synchronized (this) {
                    for (StatDisplayItem displayItem : displayItems.values()) {
                        if (displayItem.getStat().getCount() > 0) {
                            displayItem.displayNextData();
                        }
                    }
                }
            }
        };

        // Every 1000 milliseconds a new value is collected.
        timer.schedule(task, 1000, 1000);
    }
}
