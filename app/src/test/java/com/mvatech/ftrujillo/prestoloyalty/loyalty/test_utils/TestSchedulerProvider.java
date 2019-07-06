package com.mvatech.ftrujillo.prestoloyalty.loyalty.test_utils;

import com.mvatech.ftrujillo.prestoloyalty.utils.SchedulerProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TestSchedulerProvider implements SchedulerProvider {
    @Override
    public Scheduler mainThread() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }
}
