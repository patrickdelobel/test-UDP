package com.etf.test.testUdp.display;

import com.etf.test.testUdp.receiver.Receiver;
import com.etf.test.testUdp.shared.Parameters;
import com.etf.test.testUdp.server.Sender;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by patrick on 28/04/17.
 */
public class CmdPanel {
    JButton startServer = new JButton("Start server");
    JButton stopServer = new JButton("Stop server");
    JButton startReceiver = new JButton("Start receiver");
    JButton stopReceiver = new JButton("Stop receiver");

    JPanel panelCmd = null;

    private ThreadFactory threadFactory;
    private ScheduledExecutorService scheduler;

    public JPanel createPanelCmd() {
        panelCmd = new JPanel(new MigLayout(
                "",
                "",
                ""
        ));
        panelCmd.add(startServer);
        panelCmd.add(stopServer);
        panelCmd.add(startReceiver);
        panelCmd.add(stopReceiver);


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

        final ScheduledFuture<?>[] senderHandle = new ScheduledFuture<?>[1];
        final Future<?>[] receiverHandle = new Future<?>[1];
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sender sender = new Sender();
                senderHandle[0] =
                        scheduler.scheduleAtFixedRate(sender, 100, Parameters.getSendEveryMs(), MILLISECONDS);
            }
        });

        stopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                senderHandle[0].cancel(true);
            }
        });

        startReceiver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Receiver receiver = new Receiver();
                receiverHandle[0] =
                        scheduler.submit(receiver);
            }
        });

        stopReceiver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiverHandle[0].cancel(true);
            }
        });

        return panelCmd;
    }


    public JPanel getPanelCmd() {
        if (panelCmd == null)
            panelCmd = createPanelCmd();

        return panelCmd;
    }
}
