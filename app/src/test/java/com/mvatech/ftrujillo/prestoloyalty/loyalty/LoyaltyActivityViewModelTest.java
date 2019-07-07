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
        // Set up
        stubValidator(true, true, true);
        //Function call
        viewModel.submit("", "", "", "");
        //Assertions
        repositoryObserver.assertSubscribed();
    }

    @Test
    public void whenCallSubmitAndFormCorrectAndCompletableSubscribedThenTransactionStatusIsLoading() {
        // Set up
        stubValidator(true, true, true);
        LiveData<TransactionStatus> submissionStatus = viewModel.getSubmissionStatus();
        completable = Completable.create(emitter -> {
        }).doOnSubscribe(repositoryObserver::onSubscribe);
        when(repository.submitForm(any(LoyaltySignUpForm.class))).thenReturn(completable);
        //Function call
        viewModel.submit("", "", "", "");
        //Assertions
        repositoryObserver.assertSubscribed();
        assertEquals(submissionStatus.getValue(), TransactionStatus.LOADING);
    }

    @Test
    public void whenCallSubmitAndFormCorrectAndCompletableErrorThenTransactionStatusIsError() {
        // Set up
        stubValidator(true, true, true);
        LiveData<TransactionStatus> submissionStatus = viewModel.getSubmissionStatus();
        completable = Completable.error(new Throwable()).doOnSubscribe(repositoryObserver::onSubscribe);
        when(repository.submitForm(any(LoyaltySignUpForm.class))).thenReturn(completable);
        //Function call
        viewModel.submit("", "", "", "");
        //Assertions
        repositoryObserver.assertSubscribed();
        assertEquals(submissionStatus.getValue(), TransactionStatus.ERROR);
    }

    @Test
    public void whenCallSubmitAndFormCorrectAndCompletableFinishedThenTransactionStatusIsSuccess() {
        // Set up
        stubValidator(true, true, true);
        LiveData<TransactionStatus> submissionStatus = viewModel.getSubmissionStatus();
        when(repository.submitForm(any(LoyaltySignUpForm.class))).thenReturn(completable);
        //Function call
        viewModel.submit("", "", "", "");
        //Assertions
        assertEquals(submissionStatus.getValue(), TransactionStatus.SUCCESS);
    }

    @Test
    public void whenCallSubmitValidationThenStatusSendCorrectValues() {
        // Set up
        stubValidator(true, false, true);
        LiveData<FormValidationStatus> status = viewModel.getFormValidationStatus();
        //Function call
        viewModel.submit("", "", "", "");
        //Assertions
        assertTrue(Objects.requireNonNull(status.getValue()).isFirstNameValid());
        assertTrue(Objects.requireNonNull(status.getValue()).isLastNameValid());
        assertFalse(Objects.requireNonNull(status.getValue()).isEmailValid());
        assertTrue(Objects.requireNonNull(status.getValue()).isPhoneValid());
        assertFalse(status.getValue().isFormValid());
    }

    @Test
    public void whenCallSubmitAndNameIncorrectThenViewModelNotSubscribed() {
        // Set up
        stubValidator(false, true, true);
        //Function call
        viewModel.submit("", "", "", "");
        //Assertions
        repositoryObserver.assertNotSubscribed();
    }

    @Test
    public void whenCallSubmitAndEmailIncorrectThenViewModelNotSubscribed() {
        // Set up
        stubValidator(true, false, true);
        //Function call
        viewModel.submit("", "", "", "");
        //Assertions
        repositoryObserver.assertNotSubscribed();
    }

    @Test
    public void whenCallSubmitAndPhoneIncorrectThenViewModelNotSubscribed() {
        // Set up
        stubValidator(true, true, false);
        //Function call
        viewModel.submit("", "", "", "");
        //Assertions
        repositoryObserver.assertNotSubscribed();
    }

    private void stubValidator(boolean name, boolean email, boolean phone) {
        when(validator.isNameValid(any())).thenReturn(name);
        when(validator.isEmailValid(any())).thenReturn(email);
        when(validator.isPhoneValid(any())).thenReturn(phone);
    }

}