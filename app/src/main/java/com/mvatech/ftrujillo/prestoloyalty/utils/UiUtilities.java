package com.mvatech.ftrujillo.prestoloyalty.utils;

import android.widget.EditText;

public class UiUtilities {

    /**
     * @param editText Edit text to extract the content from
     * @return Content of the text box or empty if it is null
     */
    public static String extractText(EditText editText) {
        if (editText != null) {
            if (editText.getText() != null) {
                return editText.getText().toString();
            }
        }
        return "";
    }
}
