package com.kabz.blameintent.crimes;

import com.kabz.blameintent.data.CrimeRepository;

public class CrimesPresenter implements CrimesContract.Presenter {

    private final CrimeRepository     mCrimeRepository;
    private final CrimesContract.View mCrimesView;

    public CrimesPresenter(CrimeRepository crimeRepository, CrimesContract.View crimesView){
        mCrimeRepository = crimeRepository;
        mCrimesView = crimesView;
    }

    @Override
    public void start() {
        mCrimesView.setProgressIndicator(true);
        mCrimeRepository.getCrimes()
                .doOnNext(crimes -> mCrimesView.setProgressIndicator(false))
                .subscribe(crimes -> mCrimesView.show(crimes));
    }
}
