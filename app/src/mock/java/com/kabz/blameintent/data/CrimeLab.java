package com.kabz.blameintent.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CrimeLab implements CrimeRepository {

    Collection<Crime> mCrimes = new ArrayList<>();

    public CrimeLab(){
        for (int i = 0; i < 10; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0);
            mCrimes.add(c);
        }
    }

    @Override
    public Observable<Crime> getCrime(UUID crimeId) {
        Crime c = new Crime();
        c.setTitle("Crime-Number-1");
        return Observable.just(c)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Crime>> getCrimes() {
        return Observable.from(mCrimes).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
