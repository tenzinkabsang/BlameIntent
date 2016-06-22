package com.kabz.blameintent.addcrime;

import com.kabz.blameintent.BaseView;
import com.kabz.blameintent.data.Crime;

import java.util.UUID;

public interface AddCrimeContract {

    interface View extends BaseView<Crime> {

    }

    interface Presenter {
        void start(int crimeId);
    }
}

