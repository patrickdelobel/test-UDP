package etf.testUdp.swingUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static etf.testUdp.swingUtils.CommonUI.createLabel;


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

    /**
     * Installs a listener to receive notification when the text of any
     * {@code JTextComponent} is changed. Internally, it installs a
     * {@link DocumentListener} on the text component's {@link Document},
     * and a {@link PropertyChangeListener} on the text component to detect
     * if the {@code Document} itself is replaced.
     *
     * @param text any text component, such as a {@link JTextField}
     *        or {@link JTextArea}
     * @param changeListener a listener to receieve {@link ChangeEvent}s
     *        when the text is changed; the source object for the events
     *        will be the text component
     * @throws NullPointerException if either parameter is null
     */
    public static void addChangeListener(final JTextComponent text, final ChangeListener changeListener) {
        final DocumentListener dl = new DocumentListener() {
            private int lastChange = 0, lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lastChange++;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (lastNotifiedChange != lastChange) {
                            lastNotifiedChange = lastChange;
                            changeListener.stateChanged(new ChangeEvent(text));
                        }
                    }
                });
            }
        };
        text.addPropertyChangeListener("document", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Document d1 = (Document)evt.getOldValue();
                Document d2 = (Document)evt.getNewValue();
                if (d1 != null) d1.removeDocumentListener(dl);
                if (d2 != null) d2.addDocumentListener(dl);
                dl.changedUpdate(null);
            }
        });
        Document d = text.getDocument();
        if (d != null) d.addDocumentListener(dl);
    }
}
