package com.etf.test.imageLab.filters;

import com.etf.test.imageLab.ImageLabMainPanel;
import com.etf.test.swingUtils.VerticalJLabel;
import com.sun.javafx.beans.IDProperty;
import net.miginfocom.swing.MigLayout;
import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * Created by patrick on 15/05/17.
 */
@Category("none")
public abstract class AbstractFilter {
    protected Mat outputMat;

    protected JButton command;//button to show the parameters of the command
    protected JButton runCommand;//button to activate the filter
    protected JPanel commandPanel;
    protected JPanel mainCommandPanel;
    protected JPanel panelImageInput;
    protected JPanel panelImageOutput;

    protected static boolean zoomMode = true;
    String commandLabel;
    LinkedList<AbstractFilter> mainList;

    public JComponent getCommand() {
        return command;
    }

    /**
     * all filters must be listed here!
     */
    public static AbstractFilter createNewFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput,
                                                 String filterClassName, LinkedList<AbstractFilter> mainList,
                                                 ImageLabMainPanel.MousePopupListener mousePopupListener) {
        Constructor declaredConstructor = null;
        try {
            Class classToInstanciate = Class.forName(AbstractFilter.class.getPackage().getName() + "." + filterClassName);
            declaredConstructor = classToInstanciate.getDeclaredConstructor(JPanel.class, JPanel.class, JPanel.class, LinkedList.class, ImageLabMainPanel.MousePopupListener.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("not implemented A");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("not implemented B");
        }
        try {
            return (AbstractFilter) (declaredConstructor.newInstance(mainCommandPanel, panelImageInput, panelImageOutput, mainList, mousePopupListener));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("not implemented C");
    }

    public AbstractFilter(JPanel mainCommandPanel, JPanel panelImageInput, JPanel panelImageOutput,
                          String commandLabel, LinkedList<AbstractFilter> mainList,
                          ImageLabMainPanel.MousePopupListener mousePopupListener) {
        this.panelImageInput = panelImageInput;
        this.panelImageOutput = panelImageOutput;
        this.mainCommandPanel = mainCommandPanel;
        this.mainList = mainList;
        this.commandLabel = commandLabel;

        //this is the command going into the "filter's" panel
        command = new JButton(commandLabel);
        command.addActionListener(e -> {
            while (this.mainCommandPanel.getComponents().length > 0) {
                mainCommandPanel.remove(0);
            }
            mainCommandPanel.add(commandPanel, "grow");
            displayInput();
            displayOutput();
            mainCommandPanel.updateUI();
        });

        //this is the specific panel with the parameters and commands for the specific filter
        commandPanel = new JPanel(new MigLayout(
                "",
//                "[100%]",
                "[30][60%][10%]",
                ""
        ));
        runCommand = new JButton("run filter");
        runCommand.addActionListener(e -> {
            displayInput();
            run();
            displayOutput();
        });

        commandPanel.add(runCommand);

        //add in menu listener for popup
        this.getCommand().addMouseListener(mousePopupListener);
    }

    abstract public void run();

    public AbstractFilter getPrevious() {
        final int indexOfPrevious = mainList.indexOf(this) - 1;
        if (indexOfPrevious < 0)
            return null;
        AbstractFilter previous = mainList.get(indexOfPrevious);
        return previous;
    }

    public void displayInput() {
        emptyInputDisplay();

        AbstractFilter previous = getPrevious();
        if (previous == null || previous.outputMat == null)
            return;

        panelImageInput.add(matToScrollablePanel(previous.outputMat), BorderLayout.CENTER);

        panelImageInput.updateUI();
    }

    protected void emptyInputDisplay() {
        while (panelImageInput.getComponents().length > 0) {
            panelImageInput.remove(0);
        }
    }

    public void displayOutput() {
        emptyOutputDisplay();

        if (outputMat == null || outputMat.empty())
            return;

        panelImageOutput.add(matToScrollablePanel(outputMat), BorderLayout.CENTER);
        panelImageOutput.updateUI();
    }

    protected void emptyOutputDisplay() {
        while (panelImageOutput.getComponents().length > 0) {
            panelImageOutput.remove(0);
        }
    }

    public Mat getOutputMat() {
        return outputMat;
    }

    //------------- helpers ---------------
    public BufferedImage toBufferedImage(Mat matrix) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (matrix.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = matrix.channels() * matrix.cols() * matrix.rows();
        byte[] buffer = new byte[bufferSize];
        matrix.get(0, 0, buffer); // get all the pixels
        BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;
    }

    public JComponent matToScrollablePanel(Mat mat) {
        BufferedImage image = toBufferedImage(mat);

        JPanel imagePanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (zoomMode) {
                    g.drawImage(image, 0, 0, getParent().getWidth(), getParent().getHeight(), null);
                } else {
                    g.drawImage(image, 0, 0, null);
                }
            }
        };

        //TODO remove scrollPane if zoomMode
        imagePanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        JScrollPane sp = new JScrollPane(imagePanel);
        return sp;
    }


    protected void setTickLabels(JSlider slider, java.util.List<? extends Serializable> labelValuePairs) {
        Hashtable labels = new Hashtable();
        String label = null;
        int count = 0;

        for (Serializable labelValuePair : labelValuePairs) {
            if (count % 2 == 0)
                label = (String) labelValuePair;
            else
                labels.put(labelValuePair, new VerticalJLabel(label));
            count++;
        }
        slider.setLabelTable(labels);
    }

    public static boolean toggleZoomMode() {
        zoomMode = !zoomMode;
        return zoomMode;
    }

    public static boolean isZoomMode() {
        return zoomMode;
    }

    public static void setZoomMode(boolean zoomMode) {
        EqHistogramFilter.zoomMode = zoomMode;
    }

    public String getCommandLabel() {
        return commandLabel;
    }

    public String getClassification() {
        return "";
    }
}
