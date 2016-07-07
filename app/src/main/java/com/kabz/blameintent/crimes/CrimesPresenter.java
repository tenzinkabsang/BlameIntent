package com.kabz.blameintent.crimes;

import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;

public class CrimesPresenter implements CrimesContract.Presenter {

    private final CrimeRepository     mCrimeRepository;
    private final CrimesContract.View mCrimesView;

    public CrimesPresenter(CrimeRepository crimeRepository, CrimesContract.View crimesView){
        mCrimeRepository = crimeRepository;
        mCrimesView = crimesView;
    }

    @Override
    public void loadCrimes(boolean force) {
        mCrimesView.setProgressIndicator(true);
        mCrimeRepository.getCrimes(force)
                .doOnNext(crimes -> mCrimesView.setProgressIndicator(false))
                .subscribe(crimes -> mCrimesView.show(crimes));
    }

    @Override
    public void crimeSelected(Crime crime) {
        mCrimeRepository
                .getCrime(crime.getId())
                .subscribe(c -> mCrimesView.showDetail(c));
    }

    @Override
    public void showSubtitle() {
        mCrimeRepository
                .getCrimes(false)
                .subscribe(crimes -> mCrimesView.displaySubtitle(crimes.size()));
    }
}
