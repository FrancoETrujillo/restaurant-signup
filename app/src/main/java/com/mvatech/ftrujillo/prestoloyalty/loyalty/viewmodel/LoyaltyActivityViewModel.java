package com.mvatech.ftrujillo.prestoloyalty.loyalty.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mvatech.ftrujillo.prestoloyalty.data.LoyaltyRepository;
import com.mvatech.ftrujillo.prestoloyalty.data.LoyaltyRepositoryImpl;
import com.mvatech.ftrujillo.prestoloyalty.data.models.FormValidationStatus;
import com.mvatech.ftrujillo.prestoloyalty.data.models.LoyaltySignUpForm;
import com.mvatech.ftrujillo.prestoloyalty.data.models.TransactionStatus;
import com.mvatech.ftrujillo.prestoloyalty.utils.ApplicationSchedulerProvider;
import com.mvatech.ftrujillo.prestoloyalty.utils.SchedulerProvider;
import com.mvatech.ftrujillo.prestoloyalty.utils.Validator;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class LoyaltyActivityViewModel extends ViewModel {

    private LoyaltyRepository repository;
    private SchedulerProvider schedulerProvider;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private LoyaltySignUpForm signUpForm = new LoyaltySignUpForm();
    private MutableLiveData<FormValidationStatus> formValidationStatus = new MutableLiveData<>();
    private MutableLiveData<TransactionStatus> submissionStatus = new MutableLiveData<>();
    private Validator validator;

    public LoyaltyActivityViewModel(){
        repository = new LoyaltyRepositoryImpl();
        validator = new Validator();
        schedulerProvider = new ApplicationSchedulerProvider();
    }

    public LoyaltyActivityViewModel(LoyaltyRepository repository, Validator validator, SchedulerProvider schedulerProvider){
        this.repository = repository;
        this.validator = validator;
        this.schedulerProvider = schedulerProvider;
    }

    public LiveData<FormValidationStatus> getFormValidationStatus() {
        return formValidationStatus;
    }

    public LiveData<TransactionStatus> getSubmissionStatus() {
        return submissionStatus;
    }

    public void submit(String firstName, String lastName, String email, String phone) {
        signUpForm.setForm(firstName, lastName, email, phone);
        FormValidationStatus status = validateForm();
        formValidationStatus.setValue(status);

        if (status.isFormValid()) {
            // send to repository
            Timber.d("Is valid");

            Disposable requestObserver = repository.submitForm(signUpForm)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.mainThread())
                    .doOnSubscribe(d -> submissionStatus.setValue(TransactionStatus.LOADING))
                    .subscribe(
                            () -> submissionStatus.setValue(TransactionStatus.SUCCESS),
                            error -> submissionStatus.setValue(TransactionStatus.ERROR));

            subscriptions.add(requestObserver);
        }
    }

    private FormValidationStatus validateForm() {
        Timber.d("Franco %s", signUpForm);
        FormValidationStatus formStatus = new FormValidationStatus();
        formStatus.setFirstNameValid(validator.isNameValid(signUpForm.getFirstName()));
        formStatus.setLastNameValid(validator.isNameValid(signUpForm.getLastName()));
        formStatus.setEmailValid(validator.isEmailValid(signUpForm.getEmailAddress()));
        formStatus.setPhoneValid(validator.isPhoneValid(signUpForm.getPhoneNumber()));
        return formStatus;
    }

    @Override
    protected void onCleared() {
        subscriptions.dispose();
        super.onCleared();
    }
}
