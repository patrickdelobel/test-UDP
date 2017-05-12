package etf.testUdp.receiver;

import etf.testUdp.Main;
import etf.testUdp.shared.Parameters;
import etf.testUdp.utils.StatRunnable;
import io.aeron.Aeron;
import io.aeron.FragmentAssembler;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;
import org.agrona.LangUtil;
import org.agrona.concurrent.IdleStrategy;
import org.agrona.concurrent.SleepingIdleStrategy;

import java.util.concurrent.TimeUnit;

/**
 * Created by patrick on 04/05/17.
 */
public class Receiver extends StatRunnable {
    private Subscription subscription;
    private FragmentHandler fragmentHandler;

    public Receiver() {
        super(Main.sharedData.getReceiveStat(), false);

        final Aeron.Context ctx = new Aeron.Context()
                .availableImageHandler(image -> {
                    final Subscription subscription = image.subscription();
                    System.out.println(String.format(
                            "Available image on %s streamId=%d sessionId=%d from %s",
                            subscription.channel(), subscription.streamId(), image.sessionId(), image.sourceIdentity()));
                })
                .unavailableImageHandler(image -> {
                    final Subscription subscription = image.subscription();
                    System.out.println(String.format(
                            "Unavailable image on %s streamId=%d sessionId=%d",
                            subscription.channel(), subscription.streamId(), image.sessionId()));
                });
//        fragmentHandler = (buffer, offset, length, header) -> {
//            handleStat();
//
//            final byte[] data = new byte[length];
//            buffer.getBytes(offset, data);
//
////                System.out.println(String.format("Message from session %d (%d@%d) <<%s>>", header.sessionId(), length, offset, new String(data)));
//            System.out.println(String.format("Message from session %d (%d@%d)", header.sessionId(), length, offset));
//        };
        final long[] expectedPacket = new long[1];
        expectedPacket[0] = 1;
        fragmentHandler = new FragmentAssembler((buffer, offset, length, header) -> {
            handleStat(length);

            final byte[] data = new byte[length];
            buffer.getBytes(offset, data);

//                System.out.println(String.format("Message from session %d (%d@%d) <<%s>>", header.sessionId(), length, offset, new String(data)));
            long receivedPacket = buffer.getLong(0);
            if (expectedPacket[0] != receivedPacket) {
                getStat().incErrorCount();
                System.out.println(String.format("Message lost, expected msg %d - received msg %d. Message from session %d (%d@%d) lost before, first data %d",
                        expectedPacket[0], receivedPacket, header.sessionId(), length, offset, receivedPacket));
            } else {
                System.out.print(".");
            }
            expectedPacket[0] = receivedPacket + 1;
        });
//        final AtomicBoolean running = new AtomicBoolean(true);

        // Register a SIGINT handler for graceful shutdown.
//        SigInt.register(() -> running.set(false));
//        SigInt.register(this);

        // Create an Aeron instance using the configured Context and create a
        // Subscription on that instance that subscribes to the configured
        // channel and stream ID.
        // The Aeron and Subscription classes implement "AutoCloseable" and will automatically
        // clean up resources when this try block is finished
        Aeron aeron = Aeron.connect(ctx);
        subscription = aeron.addSubscription(Parameters.getChannel(), Parameters.getStream());
//        SamplesUtil.subscriberLoop(fragmentHandler, FRAGMENT_COUNT_LIMIT, running).accept(subscription);

    }

    @Override
    public long runCore() {
//        final IdleStrategy idleStrategy = new YieldingIdleStrategy();
        final IdleStrategy idleStrategy = new SleepingIdleStrategy(TimeUnit.MILLISECONDS.toNanos(1));
//        final IdleStrategy idleStrategy = new NoOpIdleStrategy();
        try {
            final int fragmentCountLimit = Parameters.getFragmentCountLimit();
            while (true)//running.get())
            {
                //System.out.print("+");
                idleStrategy.idle(subscription.poll(fragmentHandler, fragmentCountLimit));
            }
        } catch (final Exception ex) {
            LangUtil.rethrowUnchecked(ex);
        }
        System.out.println("Shutting down...");
        return -1;
    }
}

