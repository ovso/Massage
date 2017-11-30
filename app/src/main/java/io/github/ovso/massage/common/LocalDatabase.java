package io.github.ovso.massage.common;

import io.realm.Realm;
import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 11. 30
 */

public abstract class LocalDatabase<T> {

  protected Realm realm;

  public LocalDatabase() {
    realm = Realm.getDefaultInstance();
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

  public abstract void delete(T o);

  public abstract T find(String fieldName, int value);

  public abstract T get(int index);

}
