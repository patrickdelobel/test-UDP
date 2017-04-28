package etf.testUdp.display;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import static etf.testUdp.swingUtils.SwingUtils.addSeparator;

/**
 * Created by patrick on 28/04/17.
 */
public class MainPanel {
    JPanel panelParams;
    JPanel panelDisplay;
    JPanel panelCmd;
    JPanel panel;

    JTextField firstNameTextField = new JTextField();
    JTextField lastNameTextField = new JTextField();

    JLabel statSendMin = new JLabel("-");
    JLabel statSendMax = new JLabel("-");
    JLabel statSendAvg = new JLabel("-");

    JButton startServer = new JButton("Start server");
    JButton stopServer = new JButton("Stop server");
    JButton startReceiver = new JButton("Start receiver");
    JButton stopReceiver = new JButton("Stop receiver");

    public MainPanel() {
        panel = new JPanel(new MigLayout(
                "",
                "[9cm!][11cm!]",
                "[11cm][]"
        ));
        panelParams = new JPanel(new MigLayout(
                "wrap 2",
                "[] [2cm!]",
                ""
        ));
        panelDisplay = new JPanel(new MigLayout(
                "",
                "[10cm]",
                "[10cm]"
        ));
        panelCmd = new JPanel(new MigLayout(
                "",
                "",
                ""
        ));
        panelParams.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.add(panelParams);
        panelDisplay.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.add(panelDisplay, "wrap");
        panelCmd.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.add(panelCmd, "span 2, grow");

        createPanelParam();
        createPanelCmd();
    }

    private void createPanelParam() {
        addSeparator(panelParams, "Timing");
        panelParams.add(new JLabel("Update every (s)"));
        panelParams.add(firstNameTextField, "grow");
        panelParams.add(new JLabel("Send packet every (ms)"));
        panelParams.add(lastNameTextField, "grow");

        addSeparator(panelParams, "Stats");
        panelParams.add(new JLabel("Send min"));
        panelParams.add(statSendMin, "grow");
        panelParams.add(new JLabel("Send max"));
        panelParams.add(statSendMax, "grow");
        panelParams.add(new JLabel("Send avg"));
        panelParams.add(statSendAvg, "grow");
    }

    private void createPanelCmd() {
        panelCmd.add(startServer);
        panelCmd.add(stopServer);
        panelCmd.add(startReceiver);
        panelCmd.add(stopReceiver);
    }

    public JPanel getPanel() {
        return panel;
    }
}
