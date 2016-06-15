package com.kabz.blameintent.addcrime;

import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;

import java.util.UUID;

import rx.subscriptions.CompositeSubscription;

public interface AddCrimeContract {

    interface View {
        void show(Crime crime);

        void setProgressIndicator(boolean active);
    }

    interface Presenter {
        void start(UUID crimeId);
    }
}

