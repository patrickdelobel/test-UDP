package etf.testUdp.shared;

import etf.testUdp.stat.Stat;

/**
 * Created by patrick on 28/04/17.
 */
public class SharedData {
    private Stat sendStat = new Stat();
    private Stat receiveStat = new Stat();
    private int sendEveryMs = 10;

    public Stat getSendStat() {
        return sendStat;
    }

    public Stat getReceiveStat() {
        return receiveStat;
    }

    public int getSendEveryMs() {
        return sendEveryMs;
    }

    public void setSendEveryMs(int sendEveryMs) {
        this.sendEveryMs = sendEveryMs;
    }
}
