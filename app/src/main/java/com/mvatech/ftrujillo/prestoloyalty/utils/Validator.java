package com.mvatech.ftrujillo.prestoloyalty.utils;

import android.telephony.PhoneNumberUtils;
import android.util.Patterns;


public class Validator {

    /**
     * Validates if the email address provided is correct following the criteria:
     * EMAIL_ADDRESS regex pattern {@link android.util.Patterns}
     *
     * @param email email information to be checked
     * @return true if email is correct, false otherwise
     */
    public boolean isEmailValid(String email) {
        boolean isValid = false;
        if (email != null) {
            isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return isValid;
    }

    /**
     * Validates if the phone number provided is correct following the criteria:
     * phone is bigger than 7 digits
     * phone does not contains letters or symbols unless is a global number and contains '+'
     *
     * @param phone phone information to be checked
     * @return true if phone is correct, false otherwise
     */
    public boolean isPhoneValid(String phone) {
        boolean isValid = false;
        if (phone != null) {
            isValid = PhoneNumberUtils.isGlobalPhoneNumber(phone);
            isValid = isValid || phone.matches("\\d+");
            isValid = phone.length() >= 7 && isValid;
            isValid = isValid || phone.length() == 0;
        }
        return isValid;
    }

    /**
     * Validates if the name provided is correct following the criteria:
     * name does not contain digits
     * name contains more than 3 characters
     *
     * @param name name information to be checked
     * @return true if name is correct, false otherwise
     */
    public boolean isNameValid(String name) {
        boolean isValid = false;
        if (name != null) {
            isValid = name.length() > 2 && !name.matches(".*\\d.*");
        }
        return isValid;
    }

}


