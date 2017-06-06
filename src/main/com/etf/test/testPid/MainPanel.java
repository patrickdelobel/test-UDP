package com.etf.test.testPid;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by patrick on 02/06/17.
 */
public class MainPanel {
    JPanel panelParams;
    JPanel panelDisplay;
    JPanel panelCmd;
    JPanel panel;
    JSimulDisplay jSimulDisplay;
    ParamPanel paramPanel;

    public MainPanel() {
        panel = new JPanel(new MigLayout(
                "",
                "[9cm!][15cm!]",
                "[11cm][]"
        ));
        paramPanel = new ParamPanel();
        panelParams = paramPanel.getPanelParams();
        jSimulDisplay = new JSimulDisplay();

        panelDisplay = new JPanel();
        panelDisplay.setLayout(
                new MigLayout(
                        "",
                        "[15cm]",
                        "[10cm]"
                ));
        reCreatePanelDisplay();


        CmdPanel cmdPanel = new CmdPanel();

        panelCmd = cmdPanel.getPanelCmd();
        panelParams.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.add(panelParams);
        panelDisplay.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.add(panelDisplay);
        panelCmd.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.add(panelCmd, "newline, span 3,growx");

//        final StatDisplayManager statDisplayManager = new StatDisplayManager();
//        statDisplayManager.addDisplayItem(jSimulDisplay);
    }

    public void reCreatePanelDisplay() {
        JComponent chart = jSimulDisplay.createChart();
        if (panelDisplay.getComponents().length > 0)
            panelDisplay.remove(0);
        panelDisplay.add(chart, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JSimulDisplay getjSimulDisplay() {
        return jSimulDisplay;
    }

    public ParamPanel getParamPanel() {
        return paramPanel;
    }
}
