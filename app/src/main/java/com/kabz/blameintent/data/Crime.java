package com.kabz.blameintent.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.kabz.blameintent.BR;
import com.kabz.blameintent.util.Objectz;

import java.util.Date;
import java.util.UUID;

public class Crime extends BaseObservable {
    @Bindable
    private UUID mId;

    @Bindable
    private String mTitle;

    @Bindable
    private Date mDate;

    @Bindable
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        if(Objectz.equals(mTitle, title)) return;

        mTitle = title;
        notifyPropertyChanged(BR.title);
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        if(Objectz.equals(mDate, date)) return;

        mDate = date;
        notifyPropertyChanged(BR.date);
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
        notifyPropertyChanged(BR.solved);
    }
}
