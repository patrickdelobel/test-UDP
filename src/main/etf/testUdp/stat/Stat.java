package etf.testUdp.stat;

/**
 * Created by patrick on 28/04/17.
 */
public class Stat {
    private long min;
    private long max;
    private double avg;
    private long count;

    public Stat() {
        reset();
    }

    synchronized public void reset() {
        min = Long.MAX_VALUE;
        max = Long.MIN_VALUE;
        avg = 0;
        count = 0;
    }

    synchronized public void setMin(long minCandidate) {
        if (minCandidate < min)
            min = minCandidate;
    }

    synchronized public void setMax(long maxCandidate) {
        if (maxCandidate > max)
            max = maxCandidate;
    }

    synchronized public void nextValue(long value) {
        System.out.printf("count %d - value %d - avg %f .\n", count, value, avg);
        setMax(value);
        setMin(value);
        avg = (value + avg * (count - 1)) / count;//cumulative moving average discrete
        count++;
    }

    synchronized public long getMin() {
        return min;
    }

    synchronized public long getMax() {
        return max;
    }

    synchronized public double getAvg() {
        return avg;
    }

    synchronized public long getCount() {
        return count;
    }

    synchronized public void incCount() {
        count++;
    }
}
