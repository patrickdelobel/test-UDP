package etf.testUdp.receiver;

import etf.testUdp.Main;
import etf.testUdp.shared.Parameters;
import etf.testUdp.utils.StatRunnable;
import io.aeron.*;
import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;
import org.agrona.LangUtil;
import org.agrona.concurrent.IdleStrategy;
import org.agrona.concurrent.YieldingIdleStrategy;

/**
 * Created by patrick on 04/05/17.
 */
public class Receiver extends StatRunnable {
    private Subscription subscription;
    private FragmentHandler fragmentHandler;

    public Receiver() {
        super(Main.sharedData.getReceiveStat(), false);

        final Aeron.Context ctx = new Aeron.Context()
                .availableImageHandler(new AvailableImageHandler() {
                    @Override
                    public void onAvailableImage(Image image) {
                        final Subscription subscription = image.subscription();
                        System.out.println(String.format(
                                "Available image on %s streamId=%d sessionId=%d from %s",
                                subscription.channel(), subscription.streamId(), image.sessionId(), image.sourceIdentity()));
                    }
                })
                .unavailableImageHandler(new UnavailableImageHandler() {
                    @Override
                    public void onUnavailableImage(Image image) {
                        final Subscription subscription = image.subscription();
                        System.out.println(String.format(
                                "Unavailable image on %s streamId=%d sessionId=%d",
                                subscription.channel(), subscription.streamId(), image.sessionId()));
                    }
                });

        fragmentHandler = new FragmentHandler() {
            @Override
            public void onFragment(DirectBuffer buffer, int offset, int length, Header header) {
                handleStat();

                final byte[] data = new byte[length];
                buffer.getBytes(offset, data);

                System.out.println(String.format(
                        "Message from session %d (%d@%d) <<%s>>",
                        header.sessionId(), length, offset, new String(data)));
            }
        };
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
    public void runCore() {

        final IdleStrategy idleStrategy = new YieldingIdleStrategy();
        try {
            while (true)//running.get())
            {
                idleStrategy.idle(subscription.poll(fragmentHandler, Parameters.getFragmentCountLimit()));
            }
        } catch (final Exception ex) {
            LangUtil.rethrowUnchecked(ex);
        }
        System.out.println("Shutting down...");
    }
}

