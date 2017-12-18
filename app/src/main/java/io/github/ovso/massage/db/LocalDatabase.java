package io.github.ovso.massage.db;

import android.content.Context;
import io.github.ovso.massage.app.MyApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import java.util.ArrayList;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 11. 30
 */

public abstract class LocalDatabase<T> {

  protected Realm realm;
  protected RealmConfiguration configuration;
  protected Context context;

  public LocalDatabase(Context context) {
    this.context = context;
    configuration = ((MyApplication) context.getApplicationContext()).getRealmConfiguration();
    realm = Realm.getInstance(configuration);
    Timber.d(configuration.toString());
  }

  public void close() {
    realm.close();
  }

  protected void beginTransaction() {
    realm.beginTransaction();
  }

  protected void commitTransaction() {
    realm.commitTransaction();
  }

  public abstract ArrayList<T> getAll();

  public abstract int getSize();

  public abstract T add(int id);

  public abstract void delete(int id);

  public abstract T find(String fieldName, int value);

  public abstract T get(int index);

  public static void dbInfo(Context context) {
  }
}
