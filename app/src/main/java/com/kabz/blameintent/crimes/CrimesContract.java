package com.kabz.blameintent.crimes;

import com.kabz.blameintent.BaseView;
import com.kabz.blameintent.data.Crime;

import java.util.List;

public interface CrimesContract {
    interface View extends BaseView{

        void show(List<Crime> crimes);
        void showDetail(Crime c);

        void displaySubtitle(int size);
    }

    interface Presenter {

        void loadCrimes(boolean force);

        void crimeSelected(Crime crime);

        void showSubtitle();
    }
}
