package etf.testUdp.shared;

/**
 * Created by patrick on 04/05/17.
 */
public class Parameters {
//    static private String channel = "aeron:udp?endpoint=localhost:40123";
    static private String channel = "aeron:udp?endpoint=192.168.1.39:40123";
    static private int stream = 10;
    static private int fragmentCountLimit = 20;
    static private int sendEveryMs = 5;
    static int BUF_SIZE = 64 * 1024;

    public static int getFragmentCountLimit() {
        return fragmentCountLimit;
    }

    public static void setFragmentCountLimit(int fragmentCountLimit) {
        Parameters.fragmentCountLimit = fragmentCountLimit;
    }

    public static String getChannel() {
        return channel;
    }

    public static void setChannel(String channel) {
        Parameters.channel = channel;
    }

    public static int getStream() {
        return stream;
    }

    public static void setStream(int stream) {
        Parameters.stream = stream;
    }

    public static int getSendEveryMs() {
        return sendEveryMs;
    }

    public static void setSendEveryMs(int sendEveryMs) {
        Parameters.sendEveryMs = sendEveryMs;
    }

    public static int getBufSize() {
        return BUF_SIZE;
    }

    public static void setBufSize(int bufSize) {
        BUF_SIZE = bufSize;
    }
}
