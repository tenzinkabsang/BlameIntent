package com.kabz.blameintent.pager;

import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import rx.subjects.PublishSubject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrimePagerPresenterTest {

    @Mock
    CrimeRepository mCrimeRepository;

    @Mock
    CrimePagerContract.View mView;

    CrimePagerContract.Presenter mPresenter;

    @Before
    public void beforeEach(){
        mPresenter = new CrimePagerPresenter(mCrimeRepository, mView);
    }

    @Test
    public void loads_crimes_and_initializes_view() {
        List<Crime> crimes = Arrays.asList(new Crime(1), new Crime(2));

        PublishSubject<List<Crime>> subject = PublishSubject.create();
        when(mCrimeRepository.getCrimes(false)).thenReturn(subject);

        mPresenter.start();

        verify(mView).setProgressIndicator(true);

        subject.onNext(crimes);

        verify(mView).setProgressIndicator(false);
        verify(mView).init(crimes);
    }

}
