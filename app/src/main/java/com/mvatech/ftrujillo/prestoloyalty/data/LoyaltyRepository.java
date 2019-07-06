package com.mvatech.ftrujillo.prestoloyalty.data;

import com.mvatech.ftrujillo.prestoloyalty.data.models.LoyaltySignUpForm;

import io.reactivex.Completable;

public interface LoyaltyRepository {
    Completable submitForm(LoyaltySignUpForm form);
}
