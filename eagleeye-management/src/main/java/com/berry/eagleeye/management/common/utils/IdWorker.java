package com.berry.eagleeye.management.common.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/8/27 9:14
 * fileName：IdWorker
 * Use：
 */
public final class IdWorker {

    /**
     * 主机和进程的机器码
     */
    private static Sequence WORKER = new Sequence();

    /**
     * 有参构造器
     *
     * @param dataCenterId 数据中心ID
     */
    public static void initSequence(long dataCenterId) {
        WORKER = new Sequence(dataCenterId);
    }

    /**
     * 有参构造器
     *
     * @param workerId     工作机器 ID
     * @param dataCenterId 数据中心ID
     */
    public static void initSequence(long workerId, long dataCenterId) {
        WORKER = new Sequence(workerId, dataCenterId);
    }

    public static long getId() {
        return WORKER.nextId();
    }
}
