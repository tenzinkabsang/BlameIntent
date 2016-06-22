package com.kabz.blameintent.pager;

import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public interface CrimePagerContract {
    interface View {
        void init(List<Crime> crimes);
        void setProgressIndicator(boolean active);
    }

    interface Presenter {
        void start();
        void stop();
    }
}

