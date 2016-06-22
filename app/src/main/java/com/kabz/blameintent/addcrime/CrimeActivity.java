package com.kabz.blameintent.addcrime;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kabz.blameintent.R;
import com.kabz.blameintent.SingleFragmentActivity;
import com.kabz.blameintent.addcrime.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "crime-id";

    @Override
    protected Fragment getFragment() {
        int crimeId = getIntent().getIntExtra(EXTRA_CRIME_ID, -1);
        return CrimeFragment.newInstance(crimeId);
    }

    public static Intent newIntent(Context context, int crimeId) {
        return new Intent(context, CrimeActivity.class)
                .putExtra(EXTRA_CRIME_ID, crimeId);
    }
}
