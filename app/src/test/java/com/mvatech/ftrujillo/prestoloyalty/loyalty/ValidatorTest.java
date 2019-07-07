package com.mvatech.ftrujillo.prestoloyalty.loyalty;

import com.mvatech.ftrujillo.prestoloyalty.utils.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {JELLY_BEAN_MR1})
public class ValidatorTest {
    private Validator validator;

    @Before
    public void init() {
        validator = new Validator();
    }

    //email tests
    @Test
    public void whenValidatingEmailAndInputCorrectReturnTrue() {
        assertTrue(validator.isEmailValid("example@gmail.com"));
    }

    @Test
    public void whenValidatingEmailAndInputWithNoDomainReturnFalse() {
        assertFalse(validator.isEmailValid("example@"));
    }

    @Test
    public void whenValidatingEmailAndInputWithNoAtReturnFalse() {
        assertFalse(validator.isEmailValid("example"));
    }

    @Test
    public void whenValidatingEmailAndInputWithNoIdReturnFalse() {
        assertFalse(validator.isEmailValid("@gmail.com"));
    }

    @Test
    public void whenValidatingEmailAndInputIsEmptyReturnFalse() {
        assertFalse(validator.isEmailValid(""));
    }

    @Test
    public void whenValidatingEmailAndInputIsNullReturnFalse() {
        assertFalse(validator.isEmailValid(null));
    }

    //phone tests
    @Test
    public void whenValidatingPhoneAndInputCorrectReturnTrue() {
        assertTrue(validator.isPhoneValid("+11236588080"));
    }

    @Test
    public void whenValidatingPhoneAndInputCorrectNoCountryCodeReturnTrue() {
        assertTrue(validator.isPhoneValid("1236588080"));
    }

    @Test
    public void whenValidatingPhoneAndInputIsEmptyReturnTrue() {
        assertTrue(validator.isPhoneValid(""));
    }

    @Test
    public void whenValidatingPhoneAndInputTooShortReturnFalse() {
        assertFalse(validator.isPhoneValid("88080"));
    }


    @Test
    public void whenValidatingPhoneAndInputHasLettersReturnFalse() {
        assertFalse(validator.isPhoneValid("+11236c588df0"));
    }

    //name tests
    @Test
    public void whenValidatingNamesAndInputIsCorrectReturnTrue() {
        assertTrue(validator.isNameValid("Franco Trujillo"));
    }

    @Test
    public void whenValidatingNamesAndInputHasNumbersReturnFalse() {
        assertFalse(validator.isNameValid("Franco Tr85ujillo"));
    }

    @Test
    public void whenValidatingNamesAndInputSizeLessThanThreeReturnFalse() {
        assertFalse(validator.isNameValid("Fr"));
    }
}