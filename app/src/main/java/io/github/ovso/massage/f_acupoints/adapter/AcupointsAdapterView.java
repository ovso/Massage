package io.github.ovso.massage.f_acupoints.adapter;

import io.github.ovso.massage.framework.adapter.BaseAdapterView;

/**
 * Created by jaeho on 2017. 11. 28
 */

public interface AcupointsAdapterView extends BaseAdapterView {
  void refresh(int position);
  void refreshRemove();
  void refreshRemove(int position);
}
