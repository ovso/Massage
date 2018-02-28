package io.github.ovso.massage.app;

import android.app.Application;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.github.ovso.massage.BuildConfig;
import io.github.ovso.massage.di.DaggerAppComponent;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmSchema;
import lombok.Getter;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 10. 15
 */

public class MyApplication extends DaggerApplication {
  @Getter private static Application instance = null;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    initTimber();
    initRealm();
  }

  @Override protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerAppComponent.builder().application(this).build();
  }

  private void initRealm() {
    Realm.init(this);
    realmConfiguration = new RealmConfiguration.Builder().schemaVersion(0).build();
    //new RealmConfiguration.Builder().schemaVersion(0).name("massage.realm").build();
    //migrationRealm();
    //deleteRealmIfMigrationNeeded();
    //deleteRealm();
  }

  @Getter private RealmConfiguration realmConfiguration;

  private void migrationRealm() {
    realmConfiguration = new RealmConfiguration.Builder().schemaVersion(1)
        .migration((realm, oldVersion, newVersion) -> {
          RealmSchema schema = realm.getSchema();
          if (oldVersion == 0) {
            //schema.create("").addField("name", String.class).addField("age", int.class);
            //schema.get("")
            //oldVersion++;
          }
        })
        .build();
  }

  private void deleteRealmIfMigrationNeeded() {
    Realm.setDefaultConfiguration(
        new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build());
  }

  private void deleteRealm() {
    Realm.deleteRealm(new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build());
  }

  private void initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }
}