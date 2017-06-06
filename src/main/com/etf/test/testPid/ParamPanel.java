package com.etf.test.testPid;

import com.etf.test.swingUtils.JSliderWithNameAndValue;
import com.etf.test.testUdp.shared.Parameters;
import com.etf.test.testUdp.sound.SimpleSound;
import com.etf.test.testUdp.swingUtils.SwingUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by patrick on 28/04/17.
 */
public class ParamPanel {
    JPanel panelParams;

    JTextField kp = new JTextField();
    JTextField ki = new JTextField();
    JTextField kd = new JTextField();
    JSliderWithNameAndValue setPoint;
    public JLabel integral=new JLabel();
    public JLabel derivative=new JLabel();
    public JLabel error=new JLabel();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private JPanel createPanelParam() {
        panelParams = new JPanel(new MigLayout(
                "wrap 3",
                "[] [2cm!] [2cm!]",
                ""
        ));
        SwingUtils.addSeparator(panelParams, "PID parameters");
        panelParams.add(new JLabel("Kp/error"));
        panelParams.add(kp, "grow");
        panelParams.add(error, "grow");

        kp.setText("" + Parameters.getKp());
        panelParams.add(new JLabel("Ki/integral"));
        panelParams.add(ki, "grow");
        panelParams.add(integral, "grow");
        ki.setText("" + Parameters.getKi());

        panelParams.add(new JLabel("Kd/derivative"));
        panelParams.add(kd, "grow");
        panelParams.add(derivative, "grow");
        kd.setText("" + Parameters.getKd());

        setPoint = JSliderWithNameAndValue.addNewSliderToPanel2Col(panelParams, -30, 60, 30, 10, "truck speed (km/h)");
        Main.sharedData.sp = setPoint.getValue();
        setPoint.addChangeListener(e ->
                Main.sharedData.sp = setPoint.getValue());
//        scheduler.scheduleAtFixedRate(new Runnable() {
//
//            public void run() {
//                SwingUtilities.invokeLater(() -> {
//                });
//            }
//        }, 1, 1, SECONDS);

        SwingUtils.addChangeListener(kp, e -> {
            try {
                double update = Double.parseDouble(kp.getText());
                Parameters.setKp(update);
            } catch (NumberFormatException e1) {
                SimpleSound.tone(600, 200, 0.01);
            }
        });
        SwingUtils.addChangeListener(ki, e -> {
            try {
                double update = Double.parseDouble(ki.getText());
                Parameters.setKi(update);
            } catch (NumberFormatException e1) {
                SimpleSound.tone(600, 200, 0.01);
            }
        });
        SwingUtils.addChangeListener(kd, e -> {
            try {
                double update = Double.parseDouble(kd.getText());
                Parameters.setKd(update);
            } catch (NumberFormatException e1) {
                SimpleSound.tone(600, 200, 0.01);
            }
        });

        return panelParams;
    }

    public JPanel getPanelParams() {
        if (panelParams == null)
            panelParams = createPanelParam();

        return panelParams;
    }
}
