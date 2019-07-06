package com.mvatech.ftrujillo.prestoloyalty.data;

import com.mvatech.ftrujillo.prestoloyalty.data.models.LoyaltySignUpForm;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;

public class LoyaltyRepositoryImpl implements LoyaltyRepository {

    @Override
    public Completable submitForm(LoyaltySignUpForm form) {
        return Completable.create(CompletableEmitter::onComplete);
    }
}
