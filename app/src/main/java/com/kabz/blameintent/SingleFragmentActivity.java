package com.kabz.blameintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

abstract public class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment getFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm       = getSupportFragmentManager();
        Fragment        fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, getFragment())
                    .commit();
        }
    }
}
