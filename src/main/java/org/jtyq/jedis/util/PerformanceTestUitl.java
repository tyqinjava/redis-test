package org.jtyq.jedis.util;

public class PerformanceTestUitl {

    public static void test(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.println((end-start) + "毫秒");
    }
}
