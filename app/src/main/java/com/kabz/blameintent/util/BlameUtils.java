package com.kabz.blameintent.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

public final class BlameUtils {

    public static final int CLOSE = 0;

    public static void hideKeyboard(Activity activity) {
        if (activity == null) return;

        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }
        hideKeyboard(view);
    }

    private static void hideKeyboard(final View view) {
        if (view == null) return;

        Context            context            = view.getContext();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), CLOSE);
    }

    public static void showKeyboard(EditText view) {
        view.requestFocus();
        view.post(() -> {
            Context            ctx                = view.getContext();
            InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        });
    }
}
