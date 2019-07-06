package com.mvatech.ftrujillo.prestoloyalty.utils;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler mainThread();
    Scheduler io();
}
