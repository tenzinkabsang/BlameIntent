package com.kabz.blameintent.addcrime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kabz.blameintent.R;
import com.kabz.blameintent.SingleFragmentActivity;
import com.kabz.blameintent.addcrime.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return new CrimeFragment();
    }
}
