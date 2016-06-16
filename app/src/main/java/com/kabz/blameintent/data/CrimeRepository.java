package com.kabz.blameintent.data;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import rx.Observable;

public interface CrimeRepository {

    Observable<Crime> getCrime(UUID crimeId);

    Observable<List<Crime>> getCrimes();
}
