package io.github.ovso.massage.main.f_acupoints;

import android.content.res.Resources;

import androidx.annotation.StringRes;

import io.github.ovso.massage.main.f_acupoints.model.Documents;

public interface AcupointsPresenter {

  void onActivityCreate(Resources res);

  void onItemClick(Documents item);

  void onDocUrlItemClick(Documents item);

  void onDestroyView();

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
