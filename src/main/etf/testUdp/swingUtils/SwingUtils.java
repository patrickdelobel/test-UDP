package etf.testUdp.swingUtils;

import javax.swing.*;
import java.awt.*;

import static com.sun.java.swing.ui.CommonUI.createLabel;

/**
 * Created by patrick on 28/04/17.
 */
public class SwingUtils {
    static final Color LABEL_COLOR = new Color(0, 70, 213);

    static public void addSeparator(JPanel panel, String text) {
        JLabel l = createLabel(text);
        l.setForeground(LABEL_COLOR);

        panel.add(l, "gapbottom 1, span, split 2, aligny center");
        panel.add(new JSeparator(), "gapleft rel, growx");
    }
}
