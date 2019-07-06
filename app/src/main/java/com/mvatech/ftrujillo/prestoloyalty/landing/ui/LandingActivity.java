package com.mvatech.ftrujillo.prestoloyalty.landing.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mvatech.ftrujillo.prestoloyalty.R;
import com.mvatech.ftrujillo.prestoloyalty.databinding.ActivityLandingBinding;
import com.mvatech.ftrujillo.prestoloyalty.loyalty.ui.LoyaltySignUpActivity;

public class LandingActivity extends AppCompatActivity {

    ActivityLandingBinding bindings;
    Typeface playFairFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindings = DataBindingUtil.setContentView(this, R.layout.activity_landing);
        playFairFont = Typeface.createFromAsset(getAssets(), "Muli-SemiBold.ttf");
        bindings.joinProgramTextView.setTypeface(playFairFont);
        setListeners();
    }

    private void setListeners() {
        bindings.subscribeButton.setOnClickListener(this::onSubscribeButtonClick);
    }

    private void onSubscribeButtonClick(View view) {
        launchLoyaltyActivity();
    }

    private void launchLoyaltyActivity() {
        Intent loyaltyAct = new Intent(this, LoyaltySignUpActivity.class);
        startActivity(loyaltyAct);
    }


}
