package io.github.ovso.massage.f_symptom.db;

import android.content.Context;
import io.github.ovso.massage.db.LocalDatabase;
import io.github.ovso.massage.f_symptom.model.Symptom;
import io.github.ovso.massage.framework.SelectableItem;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jaeho on 2017. 11. 30
 */

public class SymptomLocalDb extends LocalDatabase<SymptomRo> {

  public SymptomLocalDb(Context context) {
    super(context);
  }

  @Override public void close() {
    super.close();
  }

  @Override public ArrayList<SymptomRo> getAll() {
    RealmResults<SymptomRo> realmResults = realm.where(SymptomRo.class).findAll();
    ArrayList<SymptomRo> all = new ArrayList<>();
    for (SymptomRo realmObject : realmResults) {
      all.add(realmObject);
    }
    return all;
  }

  @Override public int getSize() {
    return realm.where(SymptomRo.class).findAll().size();
  }

  @Override public SymptomRo add(int id) {
    beginTransaction();
    SymptomRo o = realm.createObject(SymptomRo.class);
    o.setId(id);
    commitTransaction();
    return o;
  }

  @Override public void delete(int id) {
    beginTransaction();
    realm.where(SymptomRo.class).equalTo("id", id).findFirst().deleteFromRealm();
    commitTransaction();
  }

  @Override public SymptomRo find(String fieldName, int value) {
    return realm.where(SymptomRo.class).equalTo("id", value).findFirst();
  }

  @Override public SymptomRo get(int index) {
    return getAll().get(index);
  }

  public void sort(List<SelectableItem<Symptom>> items) {
    Collections.sort(items, (o1, o2) -> {
      int a1 = o1.isFavorite() ? 0 : 1;
      int a2 = o2.isFavorite() ? 0 : 1;
      return a1 - a2;
    });
  }
}
