package com.kabz.blameintent.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class CrimeLab implements CrimeRepository {

    List<Crime> mCrimes = new ArrayList<>();

    public CrimeLab(){
        for (int i = 0; i < 20; i++) {
            Crime c = new Crime(i);
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0);
            mCrimes.add(c);
        }
    }

    @Override
    public Observable<Crime> getCrime(int crimeId) {
        Crime c = mCrimes.get(crimeId);
        return Observable.just(c)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Crime>> getCrimes() {
        return Observable.from(mCrimes)
                .toList()
                .map(crimes -> {
                    Collections.sort(crimes, (lhs, rhs) -> {
                        if(lhs.isSolved() && !rhs.isSolved())
                            return -1;

                        if(!lhs.isSolved() && rhs.isSolved())
                            return 1;

                      return 0;
                    });

                    return crimes;
                })
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
