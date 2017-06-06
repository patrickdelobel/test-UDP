package com.etf.test.testUdp.shared;

/**
 * Created by patrick on 04/05/17.
 */
public class Parameters {
        static private String channel = "aeron:udp?endpoint=localhost:40123";
//    static private String channel = "aeron:udp?endpoint=192.168.1.39:40123";
    static private int stream = 10;
    static private int fragmentCountLimit = 20;//TODO choose wisely!
    static private int sendEveryMs = 5;
    static int BUF_SIZE = 64 * 1024;
    static private int displayStatPrioRelative = Thread.NORM_PRIORITY - 2;
    static private int sendReceivePrioRelative = Thread.NORM_PRIORITY + 2;
    static private int displayStatEveryMs = 500;
    static private double kp = 0.1;
    static private double ki = 0.005;
    static private double kd = 10;

    public static int getSendReceivePrioRelative() {
        return sendReceivePrioRelative;
    }

    public static void setSendReceivePrioRelative(int sendReceivePrioRelative) {
        Parameters.sendReceivePrioRelative = sendReceivePrioRelative;
    }

    public static int getDisplayStatEveryMs() {
        return displayStatEveryMs;
    }

    public static void setDisplayStatEveryMs(int displayStatEveryMs) {
        Parameters.displayStatEveryMs = displayStatEveryMs;
    }

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

    public static int getDisplayStatPrioRelative() {
        return displayStatPrioRelative;
    }

    public static void setDisplayStatPrioRelative(int displayStatPrioRelative) {
        Parameters.displayStatPrioRelative = displayStatPrioRelative;
    }

    public static double getKp() {
        return kp;
    }

    public static void setKp(double kp) {
        Parameters.kp = kp;
    }

    public static double getKi() {
        return ki;
    }

    public static void setKi(double ki) {
        Parameters.ki = ki;
    }

    public static double getKd() {
        return kd;
    }

    public static void setKd(double kd) {
        Parameters.kd = kd;
    }
}
