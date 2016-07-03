package com.kabz.blameintent.data;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CrimeLab implements CrimeRepository {

    private final Context mContext;
    List<Crime> mCrimes = new ArrayList<>();

    public CrimeLab(Context context) {
        mContext = context;
        for (int i = 0; i < 20; i++) {
            Crime c = new Crime(i);
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0);
            mCrimes.add(c);
        }
    }

    @Override
    public Observable<Crime> getCrime(int crimeId) {
        return Observable.from(mCrimes)
                .filter(c -> c.getId() == crimeId)
                .firstOrDefault(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Crime>> getCrimes(boolean force) {
        return Observable.from(mCrimes)
                .toList()
                .map(crimes -> {
                    Collections.sort(crimes, (lhs, rhs) -> {
                        if (lhs.isSolved() && !rhs.isSolved())
                            return -1;

                        if (!lhs.isSolved() && rhs.isSolved())
                            return 1;

                        return 0;
                    });

                    return crimes;
                })
                .delay(force ? 1 : 0, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Crime> save(@NonNull Crime crime) {

        return getCrime(crime.getId())
                .doOnNext(c -> {
                    if (c == null) {
                        c = new Crime(mCrimes.size());
                        mCrimes.add(c);
                    }
                    c.setTitle(crime.getTitle());
                    c.setDate(crime.getDate());
                    c.setSolved(crime.isSolved());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> remove(int crimeId) {

        return Observable.from(mCrimes)
                .firstOrDefault(null, crime -> crime.getId() == crimeId)
                .doOnNext(crime -> {
                    if (crime != null) {
                        mCrimes.remove(crime);
                    }
                })
                .map(crime -> crime != null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public File getPhotoFile(Crime crime) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir == null) return null;

        return new File(externalFilesDir, crime.getPhotoFilename());
    }
}
