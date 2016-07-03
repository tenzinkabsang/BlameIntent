package com.kabz.blameintent;

import com.kabz.blameintent.addcrime.CrimeFragment;
import com.kabz.blameintent.addcrime.DatePickerFragment;
import com.kabz.blameintent.pager.CrimePagerActivity;
import com.kabz.blameintent.crimes.CrimeListFragment;

public interface CrimeComponent {

    void inject(CrimeFragment crimeFragment);

    void inject(CrimeListFragment crimeListFragment);

    void inject(CrimePagerActivity crimePagerActivity);
}
