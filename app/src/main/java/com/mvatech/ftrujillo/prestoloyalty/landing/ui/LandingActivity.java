package com.mvatech.ftrujillo.prestoloyalty.landing.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mvatech.ftrujillo.prestoloyalty.R;
import com.mvatech.ftrujillo.prestoloyalty.databinding.ActivityLandingBinding;
import com.mvatech.ftrujillo.prestoloyalty.loyalty.ui.LoyaltyActivity;

public class LandingActivity extends AppCompatActivity {

    ActivityLandingBinding bindings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindings = DataBindingUtil.setContentView(this, R.layout.activity_landing);
        setListeners();
    }

    private void setListeners() {
        bindings.subscribeButton.setOnClickListener(subscribeButtonListener);
    }

    private void launchLoyaltyActivity(){
        Intent loyaltyAct = new Intent(this, LoyaltyActivity.class);
        startActivity(loyaltyAct);
    }

    private View.OnClickListener subscribeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            launchLoyaltyActivity();
        }
    };
}
