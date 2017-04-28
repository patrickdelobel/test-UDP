package etf.testUdp.server;

import etf.testUdp.Main;
import etf.testUdp.shared.SharedData;

/**
 * Created by patrick on 28/04/17.
 */
public class Sender implements Runnable {
    private long lastTime = 0;//System.nanoTime();
    final private SharedData sharedData = Main.sharedData;
    private boolean initDone = false;

    @Override
    public void run() {
        long time = System.nanoTime();
        if (!initDone && sharedData.getSendStat().getCount() == 0) {
            initDone = true;
            lastTime = time;
            return;//to initialize the system properly
        }

        sharedData.getSendStat().nextValue((time - lastTime) / 1000l);
        lastTime = time;
    }
}
