package io.github.ovso.massage.f_symptom.db;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 11. 30
 */

@ToString @Data @EqualsAndHashCode(callSuper = false) public class SymptomRo extends RealmObject {
  private int id;
}
