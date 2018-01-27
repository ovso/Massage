package io.github.ovso.massage.main.f_acupoints.a_images;

import android.content.Intent;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * Created by jaeho on 2018. 1. 27
 */

public class ImagesPresenterImpl implements ImagesPresenter {

  private ImagesPresenter.View view;

  public ImagesPresenterImpl(ImagesPresenter.View view) {
    this.view = view;
  }

  @DebugLog @Override public void onCreated(Intent intent) {
    if (intent.hasExtra("items")) {
      Timber.d(intent.getSerializableExtra("items").toString());
    }
    view.setRecyclerView();
  }
}
