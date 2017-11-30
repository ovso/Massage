package io.github.ovso.massage.f_symptom.adapter;

import io.github.ovso.massage.framework.adapter.BaseAdapterView;

/**
 * Created by jaeho on 2017. 11. 28
 */

public interface SymptomAdapterView extends BaseAdapterView {
  void refresh(int position);
  void removeRefresh();

  void refreshRemove(int position);
}
