package com.kabz.blameintent;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import timber.log.Timber;

public final class MyApp extends Application {
    @Singleton
    @Component(modules = AppModule.class)
    public interface AppComponent extends CrimeComponent{
    }

    private final CrimeComponent _component = createComponent();

    public MyApp() {
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    protected CrimeComponent createComponent() {
        return DaggerMyApp_AppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public CrimeComponent getComponent() {
        return _component;
    }
}
