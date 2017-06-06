package com.etf.test.testUdp.display;

import com.etf.test.testUdp.Main;
import com.etf.test.testUdp.display.stats.JRateDisplay;
import com.etf.test.testUdp.display.stats.JStatDisplayJFreeChart2;
import com.etf.test.testUdp.display.stats.StatDisplayManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by patrick on 28/04/17.
 */
public class MainPanel {
    JPanel panelParams;
    JPanel panelDisplay;
    JPanel panelCmd;
    JPanel panel;
    JPanel panelRight;

    public MainPanel() {
        panel = new JPanel(new MigLayout(
                "",
                "[9cm!][11cm!][5cm!]",
                "[11cm][]"
        ));
        ParamPanel paramPanel = new ParamPanel();
        panelParams = paramPanel.getPanelParams();
//        panelDisplay = new JStatDisplayVisualVM(new MigLayout(
//                "",
//                "[10cm]",
//                "[10cm]"
//        ));
//        panelDisplay = new JStatDisplayJChart2D();
//        panelDisplay = new JStatDisplayJFreeChart();
        final JStatDisplayJFreeChart2 jStatDisplayJFreeChart2 = new JStatDisplayJFreeChart2(Main.sharedData.getReceiveStat(),"rate");
        final JComponent chart = jStatDisplayJFreeChart2.createChart();
        panelDisplay = new JPanel();
        panelDisplay.setLayout(
                new MigLayout(
                        "",
                        "[10cm]",
                        "[10cm]"
                ));
        panelDisplay.add(chart, BorderLayout.CENTER);
        panelRight = new JPanel();
        panelRight.setLayout(
                new MigLayout(
                        "",
                        "[5cm!]",
                        "[5cm!]"
                ));
        panelRight.setBorder(new BevelBorder(BevelBorder.LOWERED));


        final JRateDisplay jRateDisplay = new JRateDisplay(Main.sharedData.getSendStat(), "Rate sent");
//        Model model = new Model();
//        model.setRange(0, 100);
//        final AbstractGauge testGauge = new RadialBargraph(model);
//        testGauge.setUnitString("Mb/s");
//        testGauge.setTitle("Sent rate");
        panelRight.add(jRateDisplay.createChart(), "grow,wrap");
        final JRateDisplay jRateDisplayRec = new JRateDisplay(Main.sharedData.getReceiveStat(), "Rate rec");
        panelRight.add(jRateDisplayRec.createChart(), "grow");

        CmdPanel cmdPanel = new CmdPanel();

        panelCmd = cmdPanel.getPanelCmd();
        panelParams.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.add(panelParams);
        panelDisplay.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.add(panelDisplay);
        panel.add(panelRight, "wrap");
        panelCmd.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.add(panelCmd, "span 3,growx");

        final StatDisplayManager statDisplayManager = new StatDisplayManager();
        statDisplayManager.addDisplayItem(jStatDisplayJFreeChart2);
        statDisplayManager.addDisplayItem(jRateDisplay);
        statDisplayManager.addDisplayItem(jRateDisplayRec);
    }

    public JPanel getPanel() {
        return panel;
    }
}
