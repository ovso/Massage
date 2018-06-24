package io.github.ovso.massage.main.f_theme;

import android.support.annotation.StringRes;
import io.github.ovso.massage.main.f_theme.model.Theme;
import io.github.ovso.massage.framework.SelectableItem;

/**
 * Created by jaeho on 2017. 11. 27
 */

public interface ThemePresenter {

  void onActivityCreate();

  void onItemClick(SelectableItem<Theme> item);

  void onRecommendClick(int position, SelectableItem<Theme> item);

  void onFavoriteClick(int position, SelectableItem<Theme> item);

  void onVideoClick(int position, SelectableItem<Theme> item);

  void onVideoLongClick(SelectableItem<Theme> item);

  void onDestroyView();

  interface View {

    void setRecyclerView();

    void showMessage(@StringRes int resId);

    void refresh();

    void refresh(int position);

    void showVideo(String videoId);

    void showWebViewDialog(Theme item);

    void removeRefresh();

    void showMessage(String msg);

    void refreshRemove(int position);

    void showLoading();

    void hideLoading();

    void showYoutubeUseWarningDialog();

    void showLandscapeVideo(String videoId);
  }
}
