package com.kabz.blameintent;

public interface BaseView<T> {
    void show(T model);

    void setProgressIndicator(boolean active);
}
