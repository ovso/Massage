package io.github.ovso.massage.main.f_theme.adapter;

import io.github.ovso.massage.framework.adapter.BaseAdapterView;

/**
 * Created by jaeho on 2017. 11. 28
 */

public interface ThemeAdapterView extends BaseAdapterView {
  void refresh(int position);
  void refreshRemove();
  void refreshRemove(int position);
}
