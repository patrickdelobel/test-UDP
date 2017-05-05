package etf.testUdp.stat;

import etf.testUdp.shared.Parameters;

/**
 * Created by patrick on 28/04/17.
 */
public class Stat {
    private long min;
    private long max;
    private double mavg;
    private double oldMAvg;
    private double variance;
    private double oldVariance;
    private long count;

    public Stat() {
        reset();
    }

    synchronized public void reset() {
        min = Long.MAX_VALUE;
        max = Long.MIN_VALUE;
        mavg = 0;
        count = 0;
        variance = 0;
    }

    synchronized public void setMin(long minCandidate) {
        if (minCandidate < min)
            min = minCandidate;
    }

    synchronized public void setMax(long maxCandidate) {
        if (maxCandidate > max)
            max = maxCandidate;
    }

    synchronized public void update(long value) {
        setMax(value);
        setMin(value);

        if (count == 1)
        {
            oldMAvg = mavg = value;
            oldVariance = 0.0;
        }
        else
        {
            mavg = oldMAvg + (value - oldMAvg)/count;
            variance = oldVariance + (value - oldMAvg)*(value - mavg);

            // set up for next iteration
            oldMAvg = mavg;
            oldVariance = variance;
        }

        count++;
    }

    synchronized public long getMin() {
        return min;
    }

    synchronized public long getMax() {
        return max;
    }

    synchronized public double getMavg() {
        return mavg;
    }

    synchronized public long getCount() {
        return count;
    }

    synchronized public void incCount() {
        count++;
    }

    synchronized public double getVariance() {
        return variance / (count-1);
    }

    synchronized public double getStd() {
        return Math.sqrt(variance / (count-1));
    }
}
