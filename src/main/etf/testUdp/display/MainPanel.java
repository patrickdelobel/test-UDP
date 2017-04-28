package etf.testUdp.display;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Created by patrick on 28/04/17.
 */
public class MainPanel {
    JLabel firstNameLabel = new JLabel("First name");
    JTextField firstNameTextField = new JTextField();
    JLabel lastNameLabel = new JLabel("last name");
    JTextField lastNameTextField = new JTextField();
    JLabel addressLabel = new JLabel("address");
    JTextField addressTextField = new JTextField();
    JPanel panel;

    public MainPanel() {
        //MigLayout lm = new MigLayout((debug && benchRuns == 0 ? "debug, inset 20" : "ins 20"), "[para]0[][100lp, fill][60lp][95lp, fill]", "");

        panel = new JPanel(new MigLayout());

        panel.add(firstNameLabel);
        panel.add(firstNameTextField);
        panel.add(lastNameLabel, "gap unrelated");
        panel.add(lastNameTextField, "wrap");
        panel.add(addressLabel);
        panel.add(addressTextField, "span, grow");
    }

    public JPanel getPanel() {
        return panel;
    }
}
