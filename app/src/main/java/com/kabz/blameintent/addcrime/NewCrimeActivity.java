package com.kabz.blameintent.addcrime;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.kabz.blameintent.SingleFragmentActivity;

public class NewCrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return CrimeFragment.newInstance(0);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NewCrimeActivity.class);
    }
}
