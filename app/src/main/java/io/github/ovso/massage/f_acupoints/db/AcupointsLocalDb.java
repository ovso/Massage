package io.github.ovso.massage.f_acupoints.db;

import android.content.Context;
import io.github.ovso.massage.db.LocalDatabase;
import io.github.ovso.massage.f_acupoints.model.Acupoints;
import io.github.ovso.massage.framework.SelectableItem;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jaeho on 2017. 11. 30
 */

public class AcupointsLocalDb extends LocalDatabase<AcupointsRo> {

  public AcupointsLocalDb(Context context) {
    super(context);
  }

  @Override public void close() {
    super.close();
  }

  @Override public ArrayList<AcupointsRo> getAll() {
    RealmResults<AcupointsRo> realmResults = realm.where(AcupointsRo.class).findAll();
    ArrayList<AcupointsRo> all = new ArrayList<>();
    for (AcupointsRo realmObject : realmResults) {
      all.add(realmObject);
    }
    return all;
  }

  @Override public int getSize() {
    return realm.where(AcupointsRo.class).findAll().size();
  }

  @Override public AcupointsRo add(int id) {
    beginTransaction();
    AcupointsRo o = realm.createObject(AcupointsRo.class);
    o.setId(id);
    commitTransaction();
    return o;
  }

  @Override public void delete(int id) {
    beginTransaction();
    realm.where(AcupointsRo.class).equalTo("id", id).findFirst().deleteFromRealm();
    commitTransaction();
  }

  @Override public AcupointsRo find(String fieldName, int value) {
    return realm.where(AcupointsRo.class).equalTo("id", value).findFirst();
  }

  @Override public AcupointsRo get(int index) {
    return getAll().get(index);
  }

  public void sort(List<SelectableItem<Acupoints>> items) {
    Collections.sort(items, (o1, o2) -> {
      int a1 = o1.isFavorite() ? 0 : 1;
      int a2 = o2.isFavorite() ? 0 : 1;
      return a1 - a2;
    });
  }
}
