package com.kabz.blameintent.data;

import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CrimeLab implements CrimeRepository {
    @Override
    public Observable<Crime> getCrime(UUID crimeId) {
        Crime c = new Crime();
        c.setTitle("Crime-Number-1");
        return Observable.just(c)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
