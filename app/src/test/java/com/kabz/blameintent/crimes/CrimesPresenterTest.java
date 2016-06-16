package com.kabz.blameintent.crimes;

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
public class CrimesPresenterTest {
    @Mock
    CrimeRepository mCrimeRepository;
    
    @Mock
    CrimesContract.View mView;
    
    CrimesContract.Presenter mPresenter;
    
    List<Crime> mCrimes;
    
    @Before
    public void beforeEach() {
        mCrimes = Arrays.asList(
                buildCrime("crime-1", true),
                buildCrime("crime-2", false),
                buildCrime("crime-3", true)
        );
        mPresenter = new CrimesPresenter(mCrimeRepository, mView);
    }
    
    @Test
    public void list_of_crimes_passed_to_view(){
        PublishSubject<List<Crime>> subject = PublishSubject.create();
        when(mCrimeRepository.getCrimes()).thenReturn(subject);
        
        mPresenter.start();

        verify(mView).setProgressIndicator(true);

        subject.onNext(mCrimes);

        verify(mView).setProgressIndicator(false);

        verify(mView).show(mCrimes);
    }
    
    
    static Crime buildCrime(String title, boolean isSolved){
        Crime c = new Crime();
        c.setTitle(title);
        c.setSolved(isSolved);
        return c;
    }
    
}

