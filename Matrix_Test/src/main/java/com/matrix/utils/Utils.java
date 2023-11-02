package com.matrix.utils;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static boolean tryUntil(ActionWrapper func) {
        System.out.println("Try Until " + func.getName());
        Calendar stopTime = Calendar.getInstance();
        stopTime.add(Calendar.MILLISECOND, func.timeOutMs());
        int retry = 1;
        while (Calendar.getInstance().before(stopTime)) {
            System.out.println("Try Until -" + retry++);
            boolean satisfied = false;
            try {
                satisfied = func.invoke();
            } catch (Throwable e) {
                System.out.println("Try Until Exeption :" + e.getMessage());
            }
            if (satisfied) {
                return true;
            } else {
                sleep(func.delay());
            }
        }
        return false;
    }


    public static boolean tryIter(ActionWrapper func, int maxRetry) {
        System.out.println("Re Try-" + func.getName());
        for (int retry = 1; retry <= maxRetry; retry++) {
            boolean satisfied = false;
            try {
                satisfied = func.invoke();
            } catch (Throwable e) {
                System.out.println("Re try Exception :" + e.getMessage());
            }
            if (satisfied) {
                return true;
            } else {
                sleep(func.delay());
            }
        }
        return false;
    }

    public static void sleep(long timeInMSec) {
        Uninterruptibles.sleepUninterruptibly(timeInMSec, TimeUnit.MILLISECONDS);
    }
}
