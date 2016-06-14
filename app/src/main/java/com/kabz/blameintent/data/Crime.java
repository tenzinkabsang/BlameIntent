package com.kabz.blameintent.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.kabz.blameintent.BR;

import java.util.UUID;

public class Crime extends BaseObservable {
    @Bindable
    private UUID mId;

    @Bindable
    private String mTitle;

    public Crime() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        if(mTitle.equals(title)) return;

        mTitle = title;
        notifyPropertyChanged(BR.title);
    }
}
