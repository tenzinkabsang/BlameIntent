package com.kabz.blameintent.addcrime;

import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddCrimePresenterTest {

    @Mock
    CrimeRepository mCrimeRepository;

    @Mock
    AddCrimeContract.View mView;

    AddCrimePresenter mPresenter;
    Crime mCrime;

    @Before
    public void setup() {
        mCrime = new Crime(111);
        mCrime.setTitle("crime-1");
        mPresenter = new AddCrimePresenter(mCrimeRepository, mView);
    }

    @Test
    public void start_initializesViewWithTheSpecifiedCrime() {
        PublishSubject<Crime> subject = PublishSubject.create();
        when(mCrimeRepository.getCrime(any(Integer.class))).thenReturn(subject);

        mPresenter.start(111);

        subject.onNext(mCrime);
        subject.onCompleted();

        verify(mView).show(mCrime);
    }
}
