package com.etf.test.testUdp.stat;

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
    private long errorCount;
    private long retryCount;
    private long rateKbs;
    private long oldRateKbs;

    public Stat() {
        reset();
    }

    synchronized public void reset() {
        min = Long.MAX_VALUE;
        max = Long.MIN_VALUE;
        mavg = 0;
        count = 0;
        variance = 0;
        errorCount = 0;
        retryCount = 0;
        rateKbs = 0;
        oldRateKbs = 0;
    }

    synchronized public void setMin(long minCandidate) {
        if (minCandidate < min)
            min = minCandidate;
    }

    synchronized public void setMax(long maxCandidate) {
        if (maxCandidate > max)
            max = maxCandidate;
    }

    synchronized public void update(long value, long packetSizeByte) {
        setMax(value);
        setMin(value);

        if (count == 1) {
            oldMAvg = mavg = value;
//            rateKbs = oldRateKbs = ((long) ((1000000f / value) * (packetSizeByte / 1024f) * 8));
            oldVariance = 0.0;
        } else {
            mavg = oldMAvg + (value - oldMAvg) / count;
            variance = oldVariance + (value - oldMAvg) * (value - mavg);

            rateKbs = oldRateKbs + ((long) ((1000000f / value) * (packetSizeByte / 1024f) * 8) - oldRateKbs) / 10;
//System.out.print(value);

            // set up for next iteration
            oldRateKbs = rateKbs;
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

    synchronized public long getRetryCount() {
        return retryCount;
    }

    synchronized public long getErrorCount() {
        return errorCount;
    }

    synchronized public long getAndResetRateKbps() {
        long currentRate = rateKbs;
        rateKbs = 0;//next time will be null if no more packets received
        return currentRate;
    }

    synchronized public void incCount() {
        count++;
    }

    synchronized public void incRetryCount() {
        retryCount++;
    }

    synchronized public void incErrorCount() {
        errorCount++;
        rateKbs = oldRateKbs = 0;
    }

    synchronized public double getVariance() {
        return variance / (count - 1);
    }

    synchronized public double getStd() {
        return Math.sqrt(variance / (count - 1));
    }
}
