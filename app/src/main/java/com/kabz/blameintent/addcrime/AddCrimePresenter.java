package com.kabz.blameintent.addcrime;

import android.util.Log;
import android.util.Pair;

import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;

import java.io.File;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class AddCrimePresenter implements AddCrimeContract.Presenter {

    private final CompositeSubscription mSubscription;
    private final CrimeRepository       mCrimeRepository;
    private final AddCrimeContract.View mView;

    public AddCrimePresenter(CrimeRepository crimeRepository, AddCrimeContract.View view) {
        mCrimeRepository = crimeRepository;
        mView = view;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void start(int crimeId) {
        if (crimeId == -1) {
            mView.initialize(new Crime(), null);
        } else {
            mView.setProgressIndicator(true);

            Subscription sub = mCrimeRepository.getCrime(crimeId)
                    .map(crime -> {
                        File file = mCrimeRepository.getPhotoFile(crime);
                        return Pair.create(crime, file);
                    })
                    .doOnNext(c -> mView.setProgressIndicator(false))
                    .subscribe(c -> mView.initialize(c.first, c.second));
            mSubscription.add(sub);
        }

    }

    @Override
    public void stop() {
        mSubscription.unsubscribe();
        mSubscription.clear();
    }

    @Override
    public void saveCrime(Crime crime) {
        Subscription sub = mCrimeRepository.save(crime)
                .subscribe(crime1 -> mView.crimeSaved(crime1));
        mSubscription.add(sub);
    }

    @Override
    public void deleteCrime(Crime crime) {
        Subscription sub = mCrimeRepository.remove(crime.getId())
                .subscribe(b -> mView.crimeRemoved(b, crime));
        mSubscription.add(sub);
    }

    @Override
    public void chooseCrimeSuspect() {
        Log.i("AddCrimePresenter", "chooseCrimeSuspect: working......-----");
    }
}
