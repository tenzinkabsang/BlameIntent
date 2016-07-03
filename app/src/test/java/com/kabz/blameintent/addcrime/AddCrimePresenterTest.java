package com.kabz.blameintent.addcrime;

import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.subjects.PublishSubject;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
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

    @Captor
    ArgumentCaptor<Crime> mCrimeCaptor;

    @Before
    public void setup() {
        mCrime = new Crime(111);
        mCrime.setTitle("crime-1");
        mPresenter = new AddCrimePresenter(mCrimeRepository, mView);
    }

    @Test
    public void start_when_given_a_crimeId_loads_it_from_repo() {
        PublishSubject<Crime> subject = PublishSubject.create();
        when(mCrimeRepository.getCrime(any(Integer.class))).thenReturn(subject);

        mPresenter.start(111);

        verify(mView).setProgressIndicator(true);

        subject.onNext(mCrime);

        verify(mView).setProgressIndicator(false);

        verify(mView).initialize(mCrimeCaptor.capture(), null);
        assertThat(111, is(equalTo(mCrimeCaptor.getValue().getId())));
    }

    @Test
    public void start_when_no_crimeId_initializes_a_new_crime_instance(){
        mPresenter.start(0);

        verify(mView, never()).setProgressIndicator(anyBoolean());
        verify(mCrimeRepository, never()).getCrime(anyInt());

        verify(mView).initialize(mCrimeCaptor.capture(), null);
        assertEquals(0, mCrimeCaptor.getValue().getId());
    }
}
















