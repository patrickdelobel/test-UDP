package etf.testUdp.utils;

import etf.testUdp.stat.Stat;

/**
 * Created by patrick on 28/04/17.
 */
public abstract class StatRunnable implements Runnable {
    protected long lastTime = 0;
    protected long time;
    private boolean automatic;
    final protected Stat stat;

    public StatRunnable(Stat theStat, boolean automatic) {
        stat = theStat;
        stat.reset();
        this.automatic = automatic;
    }

    abstract public void runCore();

    public boolean handleStat() {
        time = System.nanoTime();
        if (stat.getCount() == 0) {//first time cannot calculate moving average
            lastTime = time;
            stat.incCount();
            return true;//to initialize the system properly, just initialized
        } else {
            stat.nextValue((time - lastTime) / 1000l);
            lastTime = time;
        }
        return false;
    }

    @Override
    public void run() {
        if (automatic) {
            handleStat();

            //actual thread work is here
            runCore();

        } else {
            //actual thread work is here, stat handling is done in the thread itself
            runCore();
        }
    }
}
