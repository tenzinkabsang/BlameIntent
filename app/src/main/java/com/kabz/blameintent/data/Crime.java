package com.kabz.blameintent.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.kabz.blameintent.BR;
import com.kabz.blameintent.util.Objectz;

import java.util.Date;
import java.util.Random;

public class Crime extends BaseObservable {
    @Bindable
    private int mId;

    @Bindable
    private String mTitle;

    @Bindable
    private Date mDate;

    @Bindable
    private boolean mSolved;

    @Bindable
    private String mSuspect;

    @Bindable
    private String mImageUrl;

    public Crime() {
        mDate = new Date();
    }

    public Crime(int id) {
        mId = id;
        mDate = new Date();
    }

    public String getPhotoFilename() {
        return "IMG_" + getId() + ".jpg";
    }

    public int getId() {
        return mId;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        if(Objectz.equals(mSuspect, suspect)) return;

        mSuspect = suspect;
        notifyPropertyChanged(BR.suspect);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        if(Objectz.equals(mTitle, title)) return;

        mTitle = title;
        notifyPropertyChanged(BR.title);
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if(Objectz.equals(mImageUrl, imageUrl)) return;

        mImageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
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
