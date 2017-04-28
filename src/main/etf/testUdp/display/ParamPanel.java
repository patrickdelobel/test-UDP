package etf.testUdp.display;

import etf.testUdp.Main;
import etf.testUdp.sound.SimpleSound;
import etf.testUdp.stat.Stat;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static etf.testUdp.swingUtils.SwingUtils.addChangeListener;
import static etf.testUdp.swingUtils.SwingUtils.addSeparator;
import static java.lang.Math.round;
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
    JLabel statSendAvg = new JLabel("-");
    JLabel statSendCount = new JLabel("-");

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private JPanel createPanelParam() {
        panelParams = new JPanel(new MigLayout(
                "wrap 2",
                "[] [2cm!]",
                ""
        ));
        addSeparator(panelParams, "Timing");
//        panelParams.add(new JLabel("Update every (s)"));
//        panelParams.add(updateEveryTextField, "grow");
        panelParams.add(new JLabel("Send packet every (ms)"));
        panelParams.add(sendEveryMsTextField, "grow");

        addSeparator(panelParams, "Stats");
        panelParams.add(new JLabel("Send min (us)"));
        panelParams.add(statSendMin, "grow");
        panelParams.add(new JLabel("Send max (us)"));
        panelParams.add(statSendMax, "grow");
        panelParams.add(new JLabel("Send avg (us)"));
        panelParams.add(statSendAvg, "grow");
        panelParams.add(new JLabel("Send count"));
        panelParams.add(statSendCount, "grow");

        sendEveryMsTextField.setText("" + Main.sharedData.getSendEveryMs());
        scheduler.scheduleAtFixedRate(new Runnable() {
            Stat sendStat = Main.sharedData.getSendStat();

            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        statSendMin.setText(String.format("%.3f", sendStat.getMin() / 1000f));
                        statSendMax.setText(String.format("%.3f", sendStat.getMax() / 1000f));
                        statSendAvg.setText(String.format("%.3f", sendStat.getAvg() / 1000f));
                        statSendCount.setText(Long.toString(sendStat.getCount()));
                    }
                });
            }
        }, 1, 1, SECONDS);

        addChangeListener(sendEveryMsTextField, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    int update = Integer.parseInt(sendEveryMsTextField.getText());
                    Main.sharedData.setSendEveryMs(update);
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
