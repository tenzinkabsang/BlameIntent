package com.kabz.blameintent.addcrime;

import com.kabz.blameintent.BaseView;
import com.kabz.blameintent.data.Crime;

import java.io.File;
import java.util.UUID;

public interface AddCrimeContract {

    interface View extends BaseView {

        void initialize(Crime crime, File photoFile);

        void crimeRemoved(Boolean success, Crime crime);

        void crimeSaved(Crime crime);
    }

    interface Presenter {
        void start(int crimeId);
        void stop();
        void saveCrime(Crime crime);

        void deleteCrime(Crime crime);
        void chooseCrimeSuspect();
    }
}

