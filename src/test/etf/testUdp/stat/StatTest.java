package etf.testUdp.stat;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by patrick on 28/04/17.
 */
public class StatTest {
    static final double EPSILON = 0.0000001;

    @Test
    public void testAvg() {
        Stat s = new Stat();
        double avg = 0;
        double total=0;
        int count=0;

        s.nextValue(2);total+=2;count++;
        assertEquals(total/count, s.getAvg(), EPSILON);

        s.nextValue(10);total+=10;count++;
        assertEquals(total/count, s.getAvg(), EPSILON);

        s.nextValue(5);total+=5;count++;
        assertEquals(total/count, s.getAvg(), EPSILON);

        s.nextValue(50);total+=50;count++;
        assertEquals(total/count, s.getAvg(), EPSILON);

        s.nextValue(1);total+=1;count++;
        assertEquals(total/count, s.getAvg(), EPSILON);
    }
}
