package com.mvatech.ftrujillo.prestoloyalty.data.models;

public class FormValidationStatus {
    private boolean firstNameValid;
    private boolean lastNameValid;
    private boolean emailValid;
    private boolean phoneValid;

    public boolean isFirstNameValid() {
        return firstNameValid;
    }

    public void setFirstNameValid(boolean firstNameValid) {
        this.firstNameValid = firstNameValid;
    }

    public boolean isLastNameValid() {
        return lastNameValid;
    }

    public void setLastNameValid(boolean lastNameValid) {
        this.lastNameValid = lastNameValid;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public void setEmailValid(boolean emailValid) {
        this.emailValid = emailValid;
    }

    public boolean isPhoneValid() {
        return phoneValid;
    }

    public void setPhoneValid(boolean phoneValid) {
        this.phoneValid = phoneValid;
    }

    public boolean isFormValid(){
        return firstNameValid && lastNameValid && emailValid && phoneValid;
    }

    @Override
    public String toString() {
        return "FormValidationStatus{" +
                "firstNameValid=" + firstNameValid +
                ", lastNameValid=" + lastNameValid +
                ", emailValid=" + emailValid +
                ", phoneValid=" + phoneValid +
                '}';
    }
}
