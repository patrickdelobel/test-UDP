package com.etf.test.swingUtils;

import javax.swing.*;

/**
 * Created by patrick on 26/05/17.
 */
public class JSliderWithNameAndValue extends JSlider {

    public JSliderWithNameAndValue(int min, int max, int value, String legend) {
        super(min, max, value);
    }

    static public JSliderWithNameAndValue addNewSliderToPanel(JPanel panel, int min, int max, int value, int majorSpacing, String legend) {
        JSliderWithNameAndValue slider = new JSliderWithNameAndValue(min, max, value, legend);
        slider.setMajorTickSpacing(majorSpacing);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        JLabel valueLabel = new JLabel("" + value);
        panel.add(new JLabel(legend), "newline");
        panel.add(slider, "grow");
        panel.add(valueLabel, "wrap");


        slider.addChangeListener(event -> {
            JSlider source = (JSlider) event.getSource();
            valueLabel.setText("" + source.getValue());
        });

        return slider;
    }

    public static JSliderWithNameAndValue addNewSliderToPanel2Col(JPanel panel, int min, int max, int value, int majorSpacing, String legend) {
        JSliderWithNameAndValue slider = new JSliderWithNameAndValue(min, max, value, legend);
        slider.setMajorTickSpacing(majorSpacing);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        JLabel valueLabel = new JLabel("" + value);
        panel.add(new JLabel(legend), "newline");
        panel.add(valueLabel, "wrap");
        panel.add(slider, "grow, span 10");//all


        slider.addChangeListener(event -> {
            JSlider source = (JSlider) event.getSource();
            valueLabel.setText("" + source.getValue());
        });

        return slider;
    }
}
