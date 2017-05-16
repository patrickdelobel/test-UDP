package com.etf.test.testUdp.display;

import com.etf.test.testUdp.Main;
import com.etf.test.testUdp.shared.Parameters;
import com.etf.test.testUdp.sound.SimpleSound;
import com.etf.test.testUdp.swingUtils.SwingUtils;
import com.etf.test.testUdp.stat.Stat;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by patrick on 28/04/17.
 */
public class ParamPanel {
    JPanel panelParams;

    //    JTextField updateEveryTextField = new JTextField();
    JTextField sendEveryMsTextField = new JTextField();

    JLabel statSendMin = new JLabel("-");
    JLabel statSendMax = new JLabel("-");
    JLabel statSendMAvg = new JLabel("-");
    JLabel statSendMStd = new JLabel("-");
    JLabel statSendCount = new JLabel("-");
    JLabel statSendRetryCount = new JLabel("-");
    JLabel statSendErrorCount = new JLabel("-");

    JLabel statRecMin = new JLabel("-");
    JLabel statRecMax = new JLabel("-");
    JLabel statRecMAvg = new JLabel("-");
    JLabel statRecMStd = new JLabel("-");
    JLabel statRecCount = new JLabel("-");
    JLabel statRecErrorCount = new JLabel("-");

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private JPanel createPanelParam() {
        panelParams = new JPanel(new MigLayout(
                "wrap 2",
                "[] [2cm!]",
                ""
        ));
        SwingUtils.addSeparator(panelParams, "Timing");
//        panelParams.add(new JLabel("Update every (s)"));
//        panelParams.add(updateEveryTextField, "grow");
        panelParams.add(new JLabel("Send packet every (ms)"));
        panelParams.add(sendEveryMsTextField, "grow");

        SwingUtils.addSeparator(panelParams, "Stats");
        panelParams.add(new JLabel("Send min (ms)"));
        panelParams.add(statSendMin, "grow");
        panelParams.add(new JLabel("Send max (ms)"));
        panelParams.add(statSendMax, "grow");
        panelParams.add(new JLabel("Send mavg (ms)"));
        panelParams.add(statSendMAvg, "grow");
        panelParams.add(new JLabel("Send mstd (ms)"));
        panelParams.add(statSendMStd, "grow");
        panelParams.add(new JLabel("Send count"));
        panelParams.add(statSendCount, "grow");
        panelParams.add(new JLabel("Send retry count"));
        panelParams.add(statSendRetryCount, "grow");
        panelParams.add(new JLabel("Send error count"));
        panelParams.add(statSendErrorCount, "grow");

        panelParams.add(new JLabel("Rec min (ms)"));
        panelParams.add(statRecMin, "grow");
        panelParams.add(new JLabel("Rec max (ms)"));
        panelParams.add(statRecMax, "grow");
        panelParams.add(new JLabel("Rec mavg (ms)"));
        panelParams.add(statRecMAvg, "grow");
        panelParams.add(new JLabel("Rec mstd (ms)"));
        panelParams.add(statRecMStd, "grow");
        panelParams.add(new JLabel("Rec count"));
        panelParams.add(statRecCount, "grow");
        panelParams.add(new JLabel("Rec error count"));
        panelParams.add(statRecErrorCount, "grow");

        sendEveryMsTextField.setText("" + Parameters.getSendEveryMs());
        scheduler.scheduleAtFixedRate(new Runnable() {
            Stat sendStat = Main.sharedData.getSendStat();
            Stat recStat = Main.sharedData.getReceiveStat();

            public void run() {
                SwingUtilities.invokeLater(() -> {
                    statSendMin.setText(String.format("%.3f", sendStat.getMin() / 1000f));
                    statSendMax.setText(String.format("%.3f", sendStat.getMax() / 1000f));
                    statSendMAvg.setText(String.format("%.3f", sendStat.getMavg() / 1000f));
                    statSendMStd.setText(String.format("%.3f", sendStat.getStd() / 1000f));
                    statSendCount.setText(Long.toString(sendStat.getCount()));
                    statSendRetryCount.setText(Long.toString(sendStat.getRetryCount()));
                    statSendErrorCount.setText(Long.toString(sendStat.getErrorCount()));

                    statRecMin.setText(String.format("%.3f", recStat.getMin() / 1000f));
                    statRecMax.setText(String.format("%.3f", recStat.getMax() / 1000f));
                    statRecMAvg.setText(String.format("%.3f", recStat.getMavg() / 1000f));
                    statRecMStd.setText(String.format("%.3f", recStat.getStd() / 1000f));
                    statRecCount.setText(Long.toString(recStat.getCount()));
                    statRecErrorCount.setText(Long.toString(recStat.getErrorCount()));
                });
            }
        }, 1, 1, SECONDS);

        SwingUtils.addChangeListener(sendEveryMsTextField, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    int update = Integer.parseInt(sendEveryMsTextField.getText());
                    Parameters.setSendEveryMs(update);
                } catch (NumberFormatException e1) {
                    SimpleSound.tone(600, 200, 0.01);
                }
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
