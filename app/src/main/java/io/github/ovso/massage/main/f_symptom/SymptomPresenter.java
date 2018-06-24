package io.github.ovso.massage.main.f_symptom;

import android.support.annotation.StringRes;
import io.github.ovso.massage.main.f_symptom.model.Symptom;
import io.github.ovso.massage.framework.SelectableItem;

/**
 * Created by jaeho on 2017. 11. 27
 */

public interface SymptomPresenter {

  void onActivityCreate();

  void onItemClick(SelectableItem<Symptom> item);

  void onRecommendClick(int position, SelectableItem<Symptom> item);

  void onFavoriteClick(SelectableItem<Symptom> item);

  void onVideoClick(int position, SelectableItem<Symptom> item);

  void onVideoLongClick(SelectableItem<Symptom> item);

  void onDestroyView();

  interface View {

    void setRecyclerView();

    void showMessage(@StringRes int resId);

    void refresh();

    void refresh(int position);

    void showVideo(String videoId);

    void showWebViewDialog(Symptom item);

    void removeRefresh();

    void showMessage(String msg);

    void refreshRemove(int position);

    void showLoading();

    void hideLoading();

    void showYoutubeUseWarningDialog();

    void showLandscapeVideo(String videoId);
  }
}
