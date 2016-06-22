package com.kabz.blameintent.crimes;

import com.kabz.blameintent.BaseView;
import com.kabz.blameintent.data.Crime;

import java.util.List;

public interface CrimesContract {
    interface View extends BaseView<List<Crime>>{

        void showDetail(Crime c);
    }

    interface Presenter {

        void loadCrimes(boolean force);

        void crimeSelected(Crime crime);
    }
}
