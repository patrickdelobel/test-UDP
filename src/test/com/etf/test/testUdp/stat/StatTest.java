package com.etf.test.testUdp.stat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by patrick on 28/04/17.
 */
public class StatTest {
    static final double EPSILON = 0.0000001;

    public static long sum(List<Long> list, int n) {

        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + list.get(i);
        }

        return sum;
    }


    public static double average(List<Long> list, double n) {
        double average = sum(list, (int) n) / n;
        return average;
    }

    public static double std(List<Long> list, double n) {
        double dv = 0, dm;
        double avg = average(list, n);
        for (int i = 0; i < n; i++) {
            dm = (list.get(i) - avg);
            dv += dm * dm;
        }

        return Math.sqrt(dv / (n - 1));
    }

    @Test
    public void testAvg() {
        Stat s = new Stat();
        double sum = 0;
        int count = 0;

        s.incCount();//init
//        List<Long> data = Arrays.asList(new Long[]{new Long(2), new Long(10), new Long(8), new Long(4), new Long(6), new Long(8), new Long(7)});
        List<Long> data = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            data.add((long) (Math.random() * 1000));

        for (int i = 0; i < data.size(); i++) {
            s.update(data.get(i), 0);
            sum += data.get(i);
            count++;
            System.out.printf("%d %d %f %f %f\n", count, sum(data, count), average(data, count), std(data, count), s.getStd());
            assertEquals(sum(data, count), sum, EPSILON);
            assertEquals(sum / count, s.getMavg(), EPSILON);
            assertEquals(average(data, count), s.getMavg(), EPSILON);
        }
        assertEquals(std(data, count), s.getStd(), average(data,count)*.01);//approximate std must be less than 1% away
    }
}
