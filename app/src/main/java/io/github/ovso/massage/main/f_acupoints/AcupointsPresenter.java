package io.github.ovso.massage.main.f_acupoints;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import io.github.ovso.massage.main.f_acupoints.model.Documents;

/**
 * Created by jaeho on 2017. 11. 27
 */

public interface AcupointsPresenter {

  void onActivityCreate(Resources res);

  void onDetach();

  void onItemClick(Documents item);

  void onDocUrlItemClick(Documents item);

  interface View {

    void setRecyclerView();

    void showMessage(@StringRes int resId);

    void refresh();

    void showMessage(String msg);

    void showLoading();

    void hideLoading();

    void showImageViewDialog(String image_url);

    void showWebViewDialog(String doc_url);
  }
}
