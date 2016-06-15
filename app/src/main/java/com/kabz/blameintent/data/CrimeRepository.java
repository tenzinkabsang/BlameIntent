package com.kabz.blameintent.data;

import java.util.UUID;

import rx.Observable;

public interface CrimeRepository {

    Observable<Crime> getCrime(UUID crimeId);
}
