package com.etf.test.testPid;

import com.etf.test.testUdp.shared.Parameters;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;

import static com.etf.test.testPid.Main.mainPanel;
import static com.etf.test.testPid.Main.sharedData;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by patrick on 28/04/17.
 */
public class CmdPanel {
    JButton startSimul = new JButton("Start simulation");
    JButton stopSimul = new JButton("Stop simulation");

    JPanel panelCmd = null;

    private ThreadFactory threadFactory;
    private ScheduledExecutorService scheduler;

    public JPanel createPanelCmd() {
        panelCmd = new JPanel(new MigLayout(
                "",
                "",
                ""
        ));
        panelCmd.add(startSimul);
        panelCmd.add(stopSimul);


        //threads management
        threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setPriority(Parameters.getSendReceivePrioRelative());
                return t;
            }
        };
        scheduler = Executors.newScheduledThreadPool(2, threadFactory);

        final ScheduledFuture<?>[] simulTruckSpeed = new ScheduledFuture<?>[1];
        final ScheduledFuture<?>[] simulPidLoop = new ScheduledFuture<?>[1];
        Parameters.setSendEveryMs(100);
        startSimul.addActionListener(e -> {
            SimulTruck simulTruck = new SimulTruck();
            SimulPid simulPid = new SimulPid();
            simulTruckSpeed[0] = scheduler.scheduleAtFixedRate(simulTruck, 100, Parameters.getSendEveryMs(), MILLISECONDS);
            simulPidLoop[0] = scheduler.scheduleAtFixedRate(simulPid, 200, Parameters.getSendEveryMs() * 10, MILLISECONDS);
        });

        stopSimul.addActionListener(e -> {
            simulTruckSpeed[0].cancel(true);
            simulPidLoop[0].cancel(true);

            sharedData.integral = 0;
            sharedData.preError = 0;
            sharedData.pv = 0;
            sharedData.output = 0;
            sharedData.noise = 0;

            mainPanel.reCreatePanelDisplay();
        });

        return panelCmd;
    }


    public JPanel getPanelCmd() {
        if (panelCmd == null)
            panelCmd = createPanelCmd();

        return panelCmd;
    }
}
