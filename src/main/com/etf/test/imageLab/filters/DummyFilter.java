package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;

import javax.swing.*;
import java.util.LinkedList;


/**
 * Created by patrick on 15/05/17.
 */
public class DummyFilter extends AbstractFilter {
    public DummyFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput,
                       LinkedList<AbstractFilter> mainList, ImageLabMainPanel.MousePopupListener mousePopupListener) {
        super(mainCommandPanel, panelImageInput, panelImageOutput, "dummy", mainList, mousePopupListener);
    }

    public void run() {
    }
}
