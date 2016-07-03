package com.kabz.blameintent.pager;

import com.kabz.blameintent.data.CrimeRepository;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class CrimePagerPresenter implements CrimePagerContract.Presenter {

    private final CrimeRepository         mCrimeRepository;
    private final CrimePagerContract.View mPagerView;
    private final CompositeSubscription   mSubscription;

    public CrimePagerPresenter(CrimeRepository crimeRepository, CrimePagerContract.View pagerView) {
        mSubscription = new CompositeSubscription();
        mCrimeRepository = crimeRepository;
        mPagerView = pagerView;
    }

    @Override
    public void start() {
        mPagerView.setProgressIndicator(true);
        Subscription sub = mCrimeRepository.getCrimes(false)
                     .subscribe(crimes -> {
                         mPagerView.setProgressIndicator(false);
                         mPagerView.init(crimes);
                     });

        mSubscription.add(sub);
    }

    @Override
    public void stop() {
        mSubscription.unsubscribe();
        mSubscription.clear();
    }
}
