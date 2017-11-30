package io.github.ovso.massage.f_symptom;

import io.github.ovso.massage.common.LocalDatabase;
import io.github.ovso.massage.f_symptom.dao.SymptomFav;
import io.github.ovso.massage.f_symptom.model.Symptom;
import io.github.ovso.massage.framework.SelectableItem;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jaeho on 2017. 11. 30
 */

public class SymptomLocalDb extends LocalDatabase<SymptomFav> {

  public SymptomLocalDb() {
    super();
  }

  @Override public void close() {
    super.close();
  }

  @Override public ArrayList<SymptomFav> getAll() {
    RealmResults<SymptomFav> realmResults = realm.where(SymptomFav.class).findAll();
    ArrayList<SymptomFav> all = new ArrayList<>();
    for (SymptomFav realmObject : realmResults) {
      all.add(realmObject);
    }
    return all;
  }

  @Override public int getSize() {
    return realm.where(SymptomFav.class).findAll().size();
  }

  @Override public SymptomFav add(int id) {
    beginTransaction();
    SymptomFav o = realm.createObject(SymptomFav.class);
    o.setId(id);
    commitTransaction();
    return o;
  }

  @Override public void delete(SymptomFav o) {
    beginTransaction();
    o.deleteFromRealm();
    commitTransaction();
  }

  @Override public SymptomFav find(String fieldName, int value) {
    return realm.where(SymptomFav.class).equalTo("id", value).findFirst();
  }

  @Override public SymptomFav get(int index) {
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
