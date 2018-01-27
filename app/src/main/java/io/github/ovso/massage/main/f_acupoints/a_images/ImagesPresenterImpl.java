package io.github.ovso.massage.main.f_acupoints.a_images;

import android.content.Intent;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.main.f_acupoints.model.Documents;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

/**
 * Created by jaeho on 2018. 1. 27
 */

public class ImagesPresenterImpl implements ImagesPresenter {

  private ImagesPresenter.View view;
  private BaseAdapterDataModel<Documents> adapterDataModel;
  private CompositeDisposable compositeDisposable;

  public ImagesPresenterImpl(ImagesPresenter.View view,
      BaseAdapterDataModel<Documents> adapterDataModel, CompositeDisposable compositeDisposable) {
    this.view = view;
    this.adapterDataModel = adapterDataModel;
    this.compositeDisposable = compositeDisposable;
  }

  @DebugLog @Override public void onCreated(Intent intent) {
    view.setRecyclerView();
    if (intent.hasExtra("items")) {
      List<Documents> items = (List<Documents>) intent.getSerializableExtra("items");
      adapterDataModel.addAll(items);
      view.refresh();
    }
  }

  @Override public void onDestroy() {
    compositeDisposable.clear();
  }
}
