package com.mvatech.ftrujillo.prestoloyalty.utils;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    /**
     * @return Provides the thread scheduler for rxJava
     */
    Scheduler mainThread();

    Scheduler io();
}
