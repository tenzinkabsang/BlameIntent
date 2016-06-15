package com.kabz.blameintent.addcrime;

import com.kabz.blameintent.data.CrimeRepository;

import java.util.UUID;

import rx.subscriptions.CompositeSubscription;

public class AddCrimePresenter implements AddCrimeContract.Presenter {

    private final CompositeSubscription mSubscription;
    private final CrimeRepository       mCrimeRepository;
    private final AddCrimeContract.View mView;

    public AddCrimePresenter(CrimeRepository crimeRepository, AddCrimeContract.View view) {
        mCrimeRepository = crimeRepository;
        mView = view;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void start(UUID crimeId) {
        mView.setProgressIndicator(true);

        mCrimeRepository.getCrime(crimeId)
                .doOnNext(crime -> mView.setProgressIndicator(false))
                .subscribe(crime ->
                    mView.show(crime)

                );

    }
}
