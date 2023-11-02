package com.matrix.utils;

public abstract class ActionWrapper {
    final public static int defaultTimeOut = 30000;//30sec
    private String name;
    private int timeOutMs = defaultTimeOut;
    private int delayMs = 1000;

    public ActionWrapper() {
        this("");
    }

    public ActionWrapper(String name) {
        this.name = name;
    }

    public ActionWrapper(String name, Integer timeOutMs) {
        this(name, timeOutMs, null);
    }

    public ActionWrapper(String name, Integer timeOutMs, Integer delayMs) {
        this.name = name;
        if (timeOutMs != null)
            this.timeOutMs = timeOutMs;
        if (delayMs != null)
            this.delayMs = delayMs;
    }

    public abstract boolean invoke() throws Exception;

    public int timeOutMs() {
        return timeOutMs;
    }

    public int delay() {
        return delayMs;
    }

    public String getName() {
        return this.name;
    }

}
