package io.github.ovso.massage.f_acupoints;

import android.support.annotation.StringRes;
import io.github.ovso.massage.f_acupoints.model.Acupoints;
import io.github.ovso.massage.framework.SelectableItem;

/**
 * Created by jaeho on 2017. 11. 27
 */

public interface AcupointsPresenter {

  void onActivityCreate();

  void onDetach();

  void onItemClick(SelectableItem<Acupoints> item);

  void onRecommendClick(int position, SelectableItem<Acupoints> item);

  void onFavoriteClick(int position, SelectableItem<Acupoints> item);

  void onVideoClick(int position, SelectableItem<Acupoints> item);

  interface View {

    void setRecyclerView();

    void showMessage(@StringRes int resId);

    void refresh();

    void refresh(int position);

    void showVideo(String videoId);

    void showWebViewDialog(Acupoints item);

    void removeRefresh();

    void showMessage(String msg);

    void refreshRemove(int position);

    void showLoading();

    void hideLoading();

    void showYoutubeUseWarningDialog();
  }
}
