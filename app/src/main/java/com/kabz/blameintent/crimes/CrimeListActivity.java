package com.kabz.blameintent.crimes;

import android.support.v4.app.Fragment;

import com.kabz.blameintent.SingleFragmentActivity;


public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return new CrimeListFragment();
    }
}
