package io.github.ovso.massage.main.f_acupoints.a_images;

import android.content.Intent;

/**
 * Created by jaeho on 2018. 1. 27..
 */

public interface ImagesPresenter {

  void onCreated(Intent intent);

  void onDestroy();

  interface View {

    void setRecyclerView();

    void refresh();
  }
}
