package etf.testUdp.shared;

import etf.testUdp.stat.Stat;

/**
 * Created by patrick on 28/04/17.
 */
public class SharedData {
    private Stat sendStat = new Stat();
    private Stat receiveStat = new Stat();

    public Stat getSendStat() {
        return sendStat;
    }

    public Stat getReceiveStat() {
        return receiveStat;
    }
}
