package io.github.ovso.massage.main.f_theme.db;

import android.content.Context;
import io.github.ovso.massage.db.LocalDatabase;
import io.github.ovso.massage.main.f_theme.model.Theme;
import io.github.ovso.massage.framework.SelectableItem;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jaeho on 2017. 11. 30
 */

public class ThemeLocalDb extends LocalDatabase<ThemeRo> {

  public ThemeLocalDb(Context context) {
    super(context);
  }

  @Override public void close() {
    super.close();
  }

  @Override public ArrayList<ThemeRo> getAll() {
    RealmResults<ThemeRo> realmResults = realm.where(ThemeRo.class).findAll();
    ArrayList<ThemeRo> all = new ArrayList<>();
    for (ThemeRo realmObject : realmResults) {
      all.add(realmObject);
    }
    return all;
  }

  @Override public int getSize() {
    return realm.where(ThemeRo.class).findAll().size();
  }

  @Override public ThemeRo add(int id) {
    beginTransaction();
    ThemeRo o = realm.createObject(ThemeRo.class);
    o.setId(id);
    commitTransaction();
    return o;
  }

  @Override public void delete(int id) {
    beginTransaction();
    realm.where(ThemeRo.class).equalTo("id", id).findFirst().deleteFromRealm();
    commitTransaction();
  }

  @Override public ThemeRo find(String fieldName, int value) {
    return realm.where(ThemeRo.class).equalTo("id", value).findFirst();
  }

  @Override public ThemeRo get(int index) {
    return getAll().get(index);
  }

  public void sort(List<SelectableItem<Theme>> items) {
    Collections.sort(items, (o1, o2) -> {
      int a1 = o1.isFavorite() ? 0 : 1;
      int a2 = o2.isFavorite() ? 0 : 1;
      return a1 - a2;
    });
  }
}
