package etf.testUdp.server;

import etf.testUdp.Main;
import etf.testUdp.shared.Parameters;
import etf.testUdp.utils.StatRunnable;
import io.aeron.Aeron;
import io.aeron.Publication;
import org.agrona.BufferUtil;
import org.agrona.concurrent.UnsafeBuffer;

/**
 * Created by patrick on 28/04/17.
 */
public class Sender extends StatRunnable {
    protected Aeron aeron;
    protected Publication publication;
    private static final UnsafeBuffer BUFFER = new UnsafeBuffer(BufferUtil.allocateDirectAligned(256, 64));

    public Sender() {
        super(Main.sharedData.getSendStat(), true);

        System.out.println("Publishing to " + Parameters.getChannel() + " on stream Id " + Parameters.getStream());

        final Aeron.Context ctx = new Aeron.Context();

        // Connect a new Aeron instance to the media driver and create a publication on
        // the given channel and stream ID.
        aeron = Aeron.connect(ctx);
        publication = aeron.addPublication(Parameters.getChannel(), Parameters.getStream());
    }

    @Override
    public void runCore() {
        final String message = "Hello World! " + stat.getCount();
        final byte[] messageBytes = message.getBytes();
        BUFFER.putBytes(0, messageBytes);

        //System.out.print("Offering " + i + "/" + NUMBER_OF_MESSAGES + " - ");

        final long result = publication.offer(BUFFER, 0, messageBytes.length);
//long result=0;
        if (result < 0L)
        {
            if (result == Publication.BACK_PRESSURED)
            {
                System.out.println("Offer failed due to back pressure");
            }
            else if (result == Publication.NOT_CONNECTED)
            {
                System.out.println("Offer failed because publisher is not connected to subscriber");
            }
            else if (result == Publication.ADMIN_ACTION)
            {
                System.out.println("Offer failed because of an administration action in the system");
            }
            else if (result == Publication.CLOSED)
            {
                System.out.println("Offer failed publication is closed");

            }
            else
            {
                System.out.println("Offer failed due to unknown reason");
            }
        }
        else
        {
            System.out.println("yay!");
        }

        if (!publication.isConnected())
        {
            System.out.println("No active subscribers detected");
        }
    }
}
