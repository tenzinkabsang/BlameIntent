package com.kabz.blameintent.crimes;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.kabz.blameintent.R;
import com.kabz.blameintent.SingleFragmentActivity;
import com.kabz.blameintent.addcrime.CrimeFragment;
import com.kabz.blameintent.addcrime.NewCrimeActivity;
import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.pager.CrimePagerActivity;


public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {
    @Override
    protected Fragment getFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (!isMasterDetailAvailable()) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onNewCrime() {
        Fragment fragment = CrimeFragment.newInstance(-1);

        final int containerId = isMasterDetailAvailable() ? R.id.detail_fragment_container : R.id.fragment_container;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    private boolean isMasterDetailAvailable() {
        return findViewById(R.id.detail_fragment_container) != null;
    }

    @Override
    public void onCrimeChanged(Crime crime) {

    }
}
