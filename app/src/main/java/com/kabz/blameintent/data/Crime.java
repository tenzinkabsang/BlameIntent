package com.kabz.blameintent.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.kabz.blameintent.BR;
import com.kabz.blameintent.util.Objectz;

import java.util.Date;
import java.util.UUID;

public class Crime extends BaseObservable {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    @Bindable
    public UUID getId() {
        return mId;
    }

    @Bindable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        if(Objectz.equals(mTitle, title)) return;

        mTitle = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        if(Objectz.equals(mDate, date)) return;

        mDate = date;
        notifyPropertyChanged(BR.date);
    }

    @Bindable
    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
        notifyPropertyChanged(BR.solved);
    }
}
