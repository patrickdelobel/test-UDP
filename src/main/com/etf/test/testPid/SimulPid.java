package com.etf.test.testPid;

import com.etf.test.testUdp.shared.Parameters;

import static com.etf.test.testPid.Main.sharedData;

/**
 * Created by patrick on 28/04/17.
 * handles simulation
 */
public class SimulPid implements Runnable {

    public SimulPid() {
    }

    /**
     * Pseudo code (source Wikipedia)
     * <p>
     * previous_error = 0<br>
     * integral = 0<br>
     * start:<br>
     * error = setpoint – PV [actual_position]<br>
     * integral = integral + error*dt<br>
     * derivative = (error - previous_error)/dt<br>
     * output = Kp*error + Ki*integral + Kd*derivative<br>
     * previous_error = error<br>
     * wait(dt)<br>
     * goto start<br>
     * <p>
     * In a real cruise control, there are limits on how much the output can change from sample to sample. This example
     * does not include any way to limit the output's magnitude of the change. Such code might look like:<br>
     * if ( (output – outputLast) > maxChange)<br>
     * output = outputLast + maxChange;<br>
     * else if ( (outputLast – output) > maxChange)<br>
     * output = outputLast – maxChange;<br>
     * outputLast = output;<br>
     * <p>
     * <br>see https://en.wikipedia.org/wiki/PID_controller#PID_controller_theory
     */
    @Override
    public void run() {
        double dt = Parameters.getSendEveryMs();//time in milliseconds

        // calculate the difference between
        // the desired value and the actual value
        double error = sharedData.sp - sharedData.pv;

        // track error over time, scaled to the timer interval
        sharedData.integral = sharedData.integral + (error * dt);

        // determine the amount of change from the last time checked
        double derivative = (error - sharedData.preError) / dt;
//        double derivative = (sharedData.pv - sharedData.prePv) / dt;

        // calculate how much to drive the output in order to get to the
        // desired setpoint.
        sharedData.output = (Parameters.getKp() * error) + (Parameters.getKi() * sharedData.integral) + (Parameters.getKd() * derivative);

        // remember the error for the next time around.
        sharedData.preError = error;
        sharedData.prePv = sharedData.pv;

        //display data for user
        Main.mainPanel.getjSimulDisplay().displayNextData(sharedData.sp, sharedData.pv);
        Main.mainPanel.getParamPanel().error.setText(String.format("%.2f", error));
        Main.mainPanel.getParamPanel().integral.setText(String.format("%.2f", sharedData.integral));
        Main.mainPanel.getParamPanel().derivative.setText(String.format("%.2f", derivative));
    }
}
