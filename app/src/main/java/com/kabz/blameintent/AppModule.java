package com.kabz.blameintent;

import android.app.Application;

import com.kabz.blameintent.data.CrimeLab;
import com.kabz.blameintent.data.CrimeRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application _app;

    public AppModule(Application app){
        _app = app;
    }

    @Provides @Singleton
    CrimeRepository provideCrimeRepository(){
        return new CrimeLab();
    }
}

