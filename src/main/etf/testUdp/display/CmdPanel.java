package etf.testUdp.display;

import etf.testUdp.Main;
import etf.testUdp.server.Sender;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

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

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

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

        final ScheduledFuture<?>[] senderHandle = new ScheduledFuture<?>[1];
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sender sender = new Sender();
                senderHandle[0] =
                        scheduler.scheduleAtFixedRate(sender, 100, Main.sharedData.getSendEveryMs(), MILLISECONDS);
            }
        });

        stopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                senderHandle[0].cancel(true);
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
