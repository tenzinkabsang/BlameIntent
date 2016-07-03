package com.kabz.blameintent.data;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import rx.Observable;

public interface CrimeRepository {

    Observable<Crime> getCrime(int crimeId);

    Observable<List<Crime>> getCrimes(boolean force);

    Observable<Crime> save(@NonNull Crime crime);

    Observable<Boolean> remove(int crimeId);

    File getPhotoFile(Crime crime);
}
