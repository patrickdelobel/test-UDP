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

    public MainPanel() {
        panel = new JPanel(new MigLayout(
                "",
                "[9cm!][11cm!]",
                "[11cm][]"
        ));
        ParamPanel paramPanel = new ParamPanel();
        panelParams = paramPanel.getPanelParams();
        panelDisplay = new JPanel(new MigLayout(
                "",
                "[10cm]",
                "[10cm]"
        ));
        CmdPanel cmdPanel = new CmdPanel();
        panelCmd = cmdPanel.getPanelCmd();
        panelParams.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.add(panelParams);
        panelDisplay.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.add(panelDisplay, "wrap");
        panelCmd.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.add(panelCmd, "span 2, grow");
    }

    public JPanel getPanel() {
        return panel;
    }
}
