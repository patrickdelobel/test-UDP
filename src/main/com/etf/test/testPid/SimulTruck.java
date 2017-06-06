package com.etf.test.testPid;

import static com.etf.test.testPid.Main.sharedData;

/**
 * Created by patrick on 28/04/17.
 */
public class SimulTruck implements Runnable {

    public SimulTruck() {
    }

    /**
     * handles simulation
     */

    /**
     * this is my version of cruise control.<br>
     * PV = PV + (output * .2) - (PV*.10);<br>
     * The equation contains values for speed, efficiency, and wind resistance.
     * Here 'PV' is the speed of the car.
     * 'output' is the amount of gas supplied to the engine.
     * (It is only 20% efficient in this example)
     * And it looses energy at 10% of the speed. (The faster the
     * car moves the more PV will be reduced.)
     * Noise is added randomly if checked, otherwise noise is 0.0
     * (Noise doesn't relate to the cruise control, but can be useful
     * when modeling other processes.)
     */
    @Override
    public void run() {
        //ProcessValue = ProcessValue + (output * efficiency) – loss
        sharedData.pv = sharedData.pv + (sharedData.output * 0.20) - (sharedData.pv * 0.10) + sharedData.noise;
    }
}
