package com.mvatech.ftrujillo.prestoloyalty.utils;

import android.telephony.PhoneNumberUtils;
import android.util.Patterns;


public class Validator {
    public boolean isEmailValid(String email){
        boolean isValid = false;
        if(email != null){
            isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return isValid;
    }

    public boolean isPhoneValid(String phone){
        boolean isValid = false;
        if(phone != null){
            isValid = PhoneNumberUtils.isGlobalPhoneNumber(phone);
            isValid = isValid || phone.matches("\\d+");
            isValid = phone.length() >= 7 && isValid;
            isValid = isValid || phone.length() == 0;
        }
        return isValid;
    }

    public boolean isNameValid(String name) {
        boolean isValid = false;
        if(name != null) {
            isValid = name.length() > 2 && !name.matches(".*\\d.*");
        }
        return isValid;
    }

}


