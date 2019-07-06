package com.mvatech.ftrujillo.prestoloyalty.loyalty.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.mvatech.ftrujillo.prestoloyalty.R;
import com.mvatech.ftrujillo.prestoloyalty.databinding.ActivityLoyaltyBinding;
import com.mvatech.ftrujillo.prestoloyalty.data.models.FormValidationStatus;
import com.mvatech.ftrujillo.prestoloyalty.data.models.TransactionStatus;
import com.mvatech.ftrujillo.prestoloyalty.loyalty.viewmodel.LoyaltyActivityViewModel;

public class LoyaltyActivity extends AppCompatActivity {
    ActivityLoyaltyBinding bindings;
    LoyaltyActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBindings();
    }

    private void setBindings() {
        bindings = DataBindingUtil.setContentView(this, R.layout.activity_loyalty);
        viewModel = ViewModelProviders.of(this).get(LoyaltyActivityViewModel.class);
        setListeners();
    }

    private void setListeners() {
        bindings.submitButton.setOnClickListener(button -> onSubmitClick());
        viewModel.getFormValidationStatus().observe(this, this::formStatusObserver);
        viewModel.getSubmissionStatus().observe(this, this::submissionStatusObserver);
    }

    private void submissionStatusObserver(TransactionStatus transactionStatus) {
        switch (transactionStatus){
            case ERROR:
                mateToast(getString(R.string.submission_error));
                break;
            case SUCCESS:
                mateToast(getString(R.string.submission_success));
                break;
            case LOADING:
                mateToast("Submitting");
                break;
        }
    }


    private void formStatusObserver(FormValidationStatus formStatus) {
        if(formStatus != null){
            bindings.firstNameLayout.setError(formStatus.isFirstNameValid()?
                    "":getString(R.string.name_msg_error));
            bindings.lastNameLayout.setError(formStatus.isLastNameValid()?
                    "":getString(R.string.name_msg_error));
            bindings.emailAddressLayout.setError(formStatus.isEmailValid()?
                    "":getString(R.string.email_msg_error));
            bindings.phoneNumberLayout.setError(formStatus.isPhoneValid()?
                    "":getString(R.string.phone_msg_error));
        }
    }


    private void onSubmitClick(){
        String firstName = extractText(bindings.firstName);
        String lastName = extractText(bindings.lastName);
        String email = extractText(bindings.emailAddress);
        String phone = extractText(bindings.phoneNumber);

        viewModel.submit(firstName, lastName, email, phone);
    }

    private String extractText(EditText editText){
        if(editText != null ){
            if(editText.getText() != null){
                return editText.getText().toString();
            }
        }
        return "";
    }

    private void mateToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
