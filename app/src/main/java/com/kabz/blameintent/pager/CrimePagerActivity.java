package com.kabz.blameintent.pager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.kabz.blameintent.MyApp;
import com.kabz.blameintent.R;
import com.kabz.blameintent.addcrime.CrimeFragment;
import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;

import java.util.List;

import javax.inject.Inject;

public class CrimePagerActivity extends AppCompatActivity implements CrimePagerContract.View {

    private static final String EXTRA_CRIME_ID = "extra-crime-id";

    @Inject
    CrimeRepository mCrimeRepository;
    private CrimePagerContract.Presenter mPresenter;
    private PageAdapter                  mPageAdapter;
    private ProgressBar                  mProgressBar;
    private ViewPager                    mViewPager;

    public static Intent newIntent(Context context, int crimeId) {
        return new Intent(context, CrimePagerActivity.class)
                .putExtra(EXTRA_CRIME_ID, crimeId);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        ((MyApp) this.getApplication()).getComponent().inject(this);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mPresenter = new CrimePagerPresenter(mCrimeRepository, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stop();
    }

    @Override
    public void init(List<Crime> crimes) {
        mPageAdapter = new PageAdapter(getSupportFragmentManager(), crimes);
        mViewPager.setAdapter(mPageAdapter);

        int crimeId         = getIntent().getIntExtra(EXTRA_CRIME_ID, -1);
        int initialPosition = mPageAdapter.getPosition(crimeId);
        mViewPager.setCurrentItem(initialPosition);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        mProgressBar.setIndeterminate(active);
        mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
        mViewPager.setVisibility(active ? View.GONE : View.VISIBLE);
    }


    private static class PageAdapter extends FragmentStatePagerAdapter {
        private final List<Crime> mCrimes;

        public PageAdapter(FragmentManager fm, List<Crime> crimes) {
            super(fm);
            mCrimes = crimes;
        }

        @Override
        public Fragment getItem(int position) {
            Crime c = mCrimes.get(position);
            return c != null ? CrimeFragment.newInstance(c.getId()) : null;
        }

        @Override
        public int getCount() {
            return mCrimes.size();
        }

        public int getPosition(int crimeId) {
            for (int i = 0; i < mCrimes.size(); i++) {
                if (mCrimes.get(i).getId() == crimeId)
                    return i;
            }
            return 0;
        }
    }
}
