package io.github.ovso.massage.f_theme;

import android.support.annotation.StringRes;
import io.github.ovso.massage.f_theme.model.Theme;
import io.github.ovso.massage.framework.SelectableItem;

/**
 * Created by jaeho on 2017. 11. 27
 */

public interface ThemePresenter {

  void onActivityCreate();

  void onDetach();

  void onItemClick(SelectableItem<Theme> item);

  void onRecommendClick(int position, SelectableItem<Theme> item);

  void onFavoriteClick(int position, SelectableItem<Theme> item);

  interface View {

    void setRecyclerView();

    void showMessage(@StringRes int resId);

    void refresh();

    void refresh(int position);

    void showVideo(String videoId);

    void showWebViewDialog(String url);

    void removeRefresh();

    void showMessage(String msg);

    void refreshRemove(int position);

    void showLoading();

    void hideLoading();
  }
}