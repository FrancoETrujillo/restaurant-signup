package com.mvatech.ftrujillo.prestoloyalty.loyalty;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.mvatech.ftrujillo.prestoloyalty.data.LoyaltyRepository;
import com.mvatech.ftrujillo.prestoloyalty.data.models.FormValidationStatus;
import com.mvatech.ftrujillo.prestoloyalty.data.models.LoyaltySignUpForm;
import com.mvatech.ftrujillo.prestoloyalty.data.models.TransactionStatus;
import com.mvatech.ftrujillo.prestoloyalty.loyalty.test_utils.TestSchedulerProvider;
import com.mvatech.ftrujillo.prestoloyalty.loyalty.viewmodel.LoyaltyActivityViewModel;
import com.mvatech.ftrujillo.prestoloyalty.utils.SchedulerProvider;
import com.mvatech.ftrujillo.prestoloyalty.utils.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Objects;

import io.reactivex.Completable;
import io.reactivex.observers.TestObserver;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LoyaltyActivityViewModelTest {

    private LoyaltyActivityViewModel viewModel;
    private Completable completable;
    private TestObserver repositoryObserver;

    @Rule
    public TestRule testRule = new InstantTaskExecutorRule();

    @Mock
    private LoyaltyRepository repository;

    @Mock
    private Validator validator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        SchedulerProvider schedulerProvider = new TestSchedulerProvider();
        viewModel = new LoyaltyActivityViewModel(repository, validator, schedulerProvider);
        repositoryObserver = TestObserver.create();
        completable = Completable.complete()
                .doOnSubscribe(repositoryObserver::onSubscribe);

        when(repository.submitForm(any(LoyaltySignUpForm.class))).thenReturn(completable);
    }

    @Test
    public void whenCallSubmitAndFormIsCorrectThenViewModelSubscribes() {
        stubValidator(true, true, true);
        viewModel.submit("", "", "", "");
        repositoryObserver.assertSubscribed();
    }

    @Test
    public void whenCallSubmitAndFormCorrectAndCompletableSubscribedThenTransactionStatusIsLoading(){
        stubValidator(true, true, true);
        LiveData<TransactionStatus> submissionStatus = viewModel.getSubmissionStatus();
        completable = Completable.create( emitter -> {}).doOnSubscribe(repositoryObserver::onSubscribe);
        when(repository.submitForm(any(LoyaltySignUpForm.class))).thenReturn(completable);
        viewModel.submit("","","","");
        repositoryObserver.assertSubscribed();
        assertEquals(submissionStatus.getValue(), TransactionStatus.LOADING);
    }

    @Test
    public void whenCallSubmitAndFormCorrectAndCompletableFinishedThenTransactionStatusIsCompleted(){
        stubValidator(true, true, true);
        LiveData<TransactionStatus> submissionStatus = viewModel.getSubmissionStatus();
        when(repository.submitForm(any(LoyaltySignUpForm.class))).thenReturn(completable);
        viewModel.submit("","","","");
        assertEquals(submissionStatus.getValue(), TransactionStatus.SUCCESS);
    }

    @Test
    public void whenCallSubmitValidationThenStatusSendCorrectValues(){
        stubValidator(true, false, true);
        LiveData<FormValidationStatus> status = viewModel.getFormValidationStatus();
        viewModel.submit("", "","","");
        assertTrue(Objects.requireNonNull(status.getValue()).isFirstNameValid());
        assertTrue(Objects.requireNonNull(status.getValue()).isLastNameValid());
        assertFalse(Objects.requireNonNull(status.getValue()).isEmailValid());
        assertTrue(Objects.requireNonNull(status.getValue()).isPhoneValid());
        assertFalse(status.getValue().isFormValid());
    }

    @Test
    public void whenCallSubmitAndNameIncorrectThenViewModelNotSubscribed() {
        stubValidator(false, true, true);
        viewModel.submit("", "", "", "");
        repositoryObserver.assertNotSubscribed();
    }

    @Test
    public void whenCallSubmitAndEmailIncorrectThenViewModelNotSubscribed() {
        stubValidator(true, false, true);
        viewModel.submit("", "", "", "");
        repositoryObserver.assertNotSubscribed();
    }

    @Test
    public void whenCallSubmitAndPhoneIncorrectThenViewModelNotSubscribed() {
        stubValidator(true, true, false);
        viewModel.submit("", "", "", "");
        repositoryObserver.assertNotSubscribed();
    }

    private void stubValidator(boolean name, boolean email, boolean phone) {
        when(validator.isNameValid(any())).thenReturn(name);
        when(validator.isEmailValid(any())).thenReturn(email);
        when(validator.isPhoneValid(any())).thenReturn(phone);
    }

}