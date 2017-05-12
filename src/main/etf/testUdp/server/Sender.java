package etf.testUdp.server;

import etf.testUdp.Main;
import etf.testUdp.shared.Parameters;
import etf.testUdp.utils.StatRunnable;
import io.aeron.Aeron;
import io.aeron.Publication;
import org.agrona.BufferUtil;
import org.agrona.concurrent.UnsafeBuffer;

import static java.lang.Thread.sleep;

/**
 * Created by patrick on 28/04/17.
 */
public class Sender extends StatRunnable {
    int BUF_SIZE;
    protected Aeron aeron;
    protected Publication publication;
    //    private static final UnsafeBuffer smallBuffer = new UnsafeBuffer(BufferUtil.allocateDirectAligned(256, 64));
    private static UnsafeBuffer bigBuffer;

    public Sender() {
        super(Main.sharedData.getSendStat(), true);

        System.out.println("Publishing to " + Parameters.getChannel() + " on stream Id " + Parameters.getStream());

        final Aeron.Context ctx = new Aeron.Context();

        // Connect a new Aeron instance to the media driver and create a publication on
        // the given channel and stream ID.
        aeron = Aeron.connect(ctx);
        publication = aeron.addPublication(Parameters.getChannel(), Parameters.getStream());

        BUF_SIZE = Parameters.getBufSize();
        bigBuffer = new UnsafeBuffer(BufferUtil.allocateDirectAligned(BUF_SIZE, 64));

        //prefill big buffer with known data
        for (int i = 0; i < BUF_SIZE / 8; i++)
            bigBuffer.byteBuffer().putLong(i);

        getStat().reset();
    }

    /**
     * handles one cycle of sending data
     */
    @Override
    public long runCore() {
        long packetSizeByte = bigBuffer.byteBuffer().limit();
//        final String message = "Hello World! " + stat.getCount();
//        final byte[] messageBytes = message.getBytes();
//        smallBuffer.putBytes(0, messageBytes);

        //System.out.print("Offering " + i + "/" + NUMBER_OF_MESSAGES + " - ");
//        final long result = publication.offer(smallBuffer, 0, messageBytes.length);
        bigBuffer.putLong(0, getStat().getCount());
        while (true) {
            final long result = publication.offer(bigBuffer, 0, bigBuffer.byteBuffer().limit());
            if (result < 0L) {
                if (result == Publication.BACK_PRESSURED) {
                    System.out.println("Offer failed due to back pressure");

                } else if (result == Publication.NOT_CONNECTED) {
                    System.out.println("Offer failed because publisher is not connected to subscriber");
                    break;
                } else if (result == Publication.ADMIN_ACTION) {
                    System.out.println("Offer failed because of an administration action in the system");
                } else if (result == Publication.CLOSED) {
                    System.out.println("Offer failed publication is closed");
                    break;

                } else {
                    System.out.println("Offer failed due to unknown reason");
                }
                try {
                    getStat().incRetryCount();
                    sleep(1);//if could not deliver for "good" reason try again 1 ms later
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            } else {
//            System.out.println("yay!");
            }

            if (!publication.isConnected()) {
                System.out.println("No active subscribers detected");
                getStat().incErrorCount();
                packetSizeByte = 0;
            }
            break;
        }
        return packetSizeByte;
    }
}
