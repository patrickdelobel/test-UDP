package com.etf.test.imageLab;

import com.etf.test.imageLab.filters.*;
import net.miginfocom.swing.MigLayout;
import org.reflections.Reflections;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * Created by patrick on 15/05/17.
 */
public class ImageLabMainPanel extends JPanel {
    JPanel panelFilters = new JPanel();
    JPanel panelFiltersGlobalCommands = new JPanel();
    //    JPanel panelImages;
    JPanel panelImageInput = new JPanel();
    JPanel panelImageOutput = new JPanel();
    JPanel panelParameters = new JPanel();
    JPopupMenu popup;
    AbstractFilter currentAbstractFilterClicked;

    LinkedList<AbstractFilter> filters = new LinkedList<>();

    public ImageLabMainPanel() throws HeadlessException {
        super(new MigLayout(
                "",
//                "[100%]",
                "[90%][10%]",
//                "[10%:10%:20%][30%:30%:80%][30%:80%:100%]"
                "[20%][80%:80%:80%]"
        ));

        //create parameters panel
        panelParameters.setLayout(
                new MigLayout(
                        "",
                        "[100%]",
                        "[]"
                ));
        panelParameters.setBorder(new BevelBorder(BevelBorder.LOWERED));

        //popup management
        final MousePopupListener mousePopupListener = new MousePopupListener();
        popup = new JPopupMenu();
        JMenu insertBefore = new JMenu("insert before");
        JMenu insertAfter = new JMenu("insert after");
        JMenuItem delete = new JMenuItem("delete");
        popup.add(insertBefore);
        popup.add(insertAfter);
        popup.add(delete);
        HashMap<String, JMenu> categories = new HashMap<>();
        HashMap<String, JMenu> categoriesCopy = new HashMap<>();

        //create filters
        //TODO temp DummyFilter dummyFilter = new DummyFilter(panelParameters, panelImageInput, panelImageOutput, filters, mousePopupListener);
//        filters.add(dummyFilter);//start empty

        java.util.List<String> predef1 = Arrays.asList("ImageLoader", "BlurFilter", "AdaptiveThresholdFilter", "CannyFilter", "FindContourFilter");
        java.util.List<String> predef2 = Arrays.asList("CameraLoader", "BlurFilter", "LaplacianFilter");
        java.util.List<String> predef3 = Arrays.asList("ImageLoader", "BlurFilter", "LaplacianFilter");
        java.util.List<String> predef4 = Arrays.asList("ImageLoader", "BlurFilter", "AdaptiveThresholdFilter", "ConnectedComponentsFilter");
        for (String filter : predef4) {
            filters.add(AbstractFilter.createNewFilter(panelParameters, panelImageInput, panelImageOutput, filter, filters, mousePopupListener));
        }
        JMenuItem item;
        Reflections reflections = new Reflections(AbstractFilter.class.getPackage().getName());
        Set<Class<? extends AbstractFilter>> filterClasses = reflections.getSubTypesOf(AbstractFilter.class);

        SortedSet<String> filterClassesSorted = new TreeSet<>();
        for (Class aClass : filterClasses) {
            if (!aClass.getSimpleName().equalsIgnoreCase("DummyFilter")) {
                String cat = aClass.getAnnotation(Category.class).toString().split("=")[1].replace(")", "");
                if (!categories.containsKey(cat)) {
                    categories.put(cat, new JMenu(cat));
                    categoriesCopy.put(cat, new JMenu(cat));
                }
                filterClassesSorted.add(cat + "/" + aClass.getSimpleName());
//                filterClassesSorted.add(aClass.getSimpleName());
            }
        }
        for (JMenu jMenu : categories.values()) {
            insertBefore.add(jMenu);
        }
        for (JMenu jMenu : categoriesCopy.values()) {
            insertAfter.add(jMenu);
        }
        ActionListener menuListener = event -> {
            int indexOfFilter = filters.indexOf(currentAbstractFilterClicked);

            if (((JMenuItem) event.getSource()).getText().contains("delete")) {
                if (!currentAbstractFilterClicked.getCommandLabel().equalsIgnoreCase("dummy")) {
                    if (currentAbstractFilterClicked instanceof CameraLoader)
                        ((CameraLoader) currentAbstractFilterClicked).release();
                    filters.remove(currentAbstractFilterClicked);

                    if (filters.size() == 0)
                        filters.add(indexOfFilter, new DummyFilter(panelParameters, panelImageInput, panelImageOutput, filters, mousePopupListener));
                }
            } else {
                boolean before = ((JMenu)((JPopupMenu) ((JPopupMenu) ((JMenuItem) event.getSource()).getParent()).getInvoker().getParent()).getInvoker()).getText().contains("before");

                if (currentAbstractFilterClicked.getCommandLabel().equalsIgnoreCase("dummy")) {
                    filters.remove(currentAbstractFilterClicked);
                } else {
                    if (before) {
                    } else {
                        indexOfFilter++;
                    }
                }
                filters.add(indexOfFilter, AbstractFilter.createNewFilter(panelParameters, panelImageInput, panelImageOutput, event.getActionCommand(), filters, mousePopupListener));
            }
            redisplayAllFilters();
        };

        for (String filterName : filterClassesSorted) {
            String cat = filterName.split("/")[0];
            String menu = filterName.split("/")[1];

            categories.get(cat).add(item = new JMenuItem(menu));
            item.addActionListener(menuListener);
            categoriesCopy.get(cat).add(item = new JMenuItem(menu));
            item.addActionListener(menuListener);
        }
        delete.addActionListener(menuListener);

        //create the main panels of the application
        //filers panel
        panelFilters.setLayout(
                new MigLayout(
                        "",
                        "[]",
                        "[]"
                ));
        panelFilters.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panelFiltersGlobalCommands.setBorder(new BevelBorder(BevelBorder.LOWERED));
        redisplayAllFilters();

        //global commands
        JButton runAll = new JButton("run all");
        panelFiltersGlobalCommands.add(runAll);
        runAll.addActionListener(e -> {
            filters.getFirst().displayInput();
            for (AbstractFilter af : filters) {
                af.run();
            }
            filters.getLast().displayOutput();
        });
        JToggleButton runContinuous = new JToggleButton("run loop", false);
        panelFiltersGlobalCommands.add(runContinuous);
        Runnable loopRunnable = () -> {
            while (runContinuous.isSelected()) {
                filters.getFirst().displayInput();
                for (AbstractFilter af : filters) {
                    af.run();
                }
                filters.getLast().displayOutput();
            }
        };
        runContinuous.addActionListener(e -> {
            if (runContinuous.isSelected()) {
                Thread loopThread = new Thread(loopRunnable);
                loopThread.start();
            }
        });
        JToggleButton zoom = new JToggleButton("zoom");
        panelFiltersGlobalCommands.add(zoom);
        zoom.addActionListener(e -> {
            AbstractFilter.toggleZoomMode();
            //TODO redisplay current tool
        });

        //images panel
//        panelImages = new JPanel();
//        panelImages.setLayout(
//                new MigLayout(
//                        "",
//                        "[grow][grow]",//                        "[50%][50%]",
//                        "[grow]"
//                ));
//        panelImages.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panelImageInput.setBorder(new BevelBorder(BevelBorder.RAISED));
        panelImageInput.setLayout(new BorderLayout());
        panelImageOutput.setBorder(new BevelBorder(BevelBorder.RAISED));
        panelImageOutput.setLayout(new BorderLayout());

//        panelImages.add(panelImageInput, "growx, growy");
        final JFrame inputFrame = new JFrame("input");
        inputFrame.add(panelImageInput);
        inputFrame.setBounds(1, 1, 400, 300);
        inputFrame.setVisible(true);
//        panelImages.add(panelImageOutput, "growx, growy");

//        panelImages.add(panelImageOutput, "growx, growy");
        final JFrame outputFrame = new JFrame("output");
        outputFrame.add(panelImageOutput);
        outputFrame.setBounds(501, 1, 400, 300);
        outputFrame.setVisible(true);

        add(panelFilters, "growx");
        add(panelFiltersGlobalCommands, "wrap");
        add(panelParameters, "wrap, spanx 2, grow");
//        add(panelImages, "spanx 2, growx");
    }

    public void redisplayAllFilters() {
        panelFilters.removeAll();
        for (AbstractFilter af : filters) {
            panelFilters.add(af.getCommand());
        }
        panelFilters.updateUI();
    }

    // An inner class to check whether mouse events are the popup trigger
    public class MousePopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            checkPopup(e);
        }

        public void mouseClicked(MouseEvent e) {
            checkPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            checkPopup(e);
        }

        private void checkPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                final JButton sourceButton = (JButton) e.getSource();
                currentAbstractFilterClicked = null;
                for (AbstractFilter af : filters) {
                    if (af.getCommand() == sourceButton) {
                        currentAbstractFilterClicked = af;
                        break;
                    }
                }
                popup.show(sourceButton, e.getX(), e.getY());
            }
        }
    }
}
