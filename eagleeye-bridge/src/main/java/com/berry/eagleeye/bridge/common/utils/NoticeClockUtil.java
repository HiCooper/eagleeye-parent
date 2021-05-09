package com.berry.eagleeye.bridge.common.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2021/2/8 19:55
 * fileName：NoticeClockUtil
 * Use：回调通知时钟工具类
 */
public class NoticeClockUtil {

    public static final int NOTIFICATION_MAX_TIMES;

    /**
     * 回调通知频率
     * 15s/15s/30s/3m/10m/20m/30m/30m/30m/60m/3h/3h/3h/6h/6h
     */
    private static final int[] NOTIFICATION_FREQUENCY = {
            15, 15, 30,
            3 * 60, 10 * 60, 20 * 60, 30 * 60, 30 * 60, 30 * 60,
            60 * 60, 3 * 60 * 60, 3 * 60 * 60, 6 * 60 * 60, 6 * 60 * 60
    };

    static {
        NOTIFICATION_MAX_TIMES = NOTIFICATION_FREQUENCY.length;
    }

    /**
     * 获取下一个通知时间间距
     * 如果 返回 0 说明超过最大通知次数不再执行通知
     * @param frequency 当前已通知次数
     * @return gap
     */
    public static int getNextClockGap(int frequency) {
        if (frequency < 1) {
            return NOTIFICATION_FREQUENCY[0];
        }
        if (frequency >= NOTIFICATION_MAX_TIMES) {
            return 0;
        }
        return NOTIFICATION_FREQUENCY[frequency];
    }
}
