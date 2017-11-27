package io.github.ovso.massage.app;

import android.app.Activity;
import android.app.Application;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.github.ovso.massage.BuildConfig;
import io.github.ovso.massage.di.DaggerAppComponent;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 10. 15
 */

public class MyApplication extends Application implements HasActivityInjector {

  @Inject DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

  @Override public void onCreate() {
    super.onCreate();
    initDagger();
    initTimber();
  }

  private void initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  private void initDagger() {
    DaggerAppComponent.builder().application(this).build().inject(this);
  }

  @Override public AndroidInjector<Activity> activityInjector() {
    return activityDispatchingAndroidInjector;
  }
}