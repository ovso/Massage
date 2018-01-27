package io.github.ovso.massage.main.f_acupoints;

import android.content.res.Resources;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.main.f_acupoints.model.Documents;
import io.github.ovso.massage.main.f_acupoints.network.ImagesNetwork;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class AcupointsPresenterImpl implements AcupointsPresenter {
  private View view;
  private CompositeDisposable compositeDisposable;
  private BaseAdapterDataModel<Documents> adapterDataModel;
  private ImagesNetwork imagesNetwork;

  public AcupointsPresenterImpl(View view, BaseAdapterDataModel adapterDataModel,
      CompositeDisposable compositeDisposable, ImagesNetwork imagesNetwork) {
    this.view = view;
    this.adapterDataModel = adapterDataModel;
    this.compositeDisposable = compositeDisposable;
    this.imagesNetwork = imagesNetwork;
  }

  @Override public void onActivityCreate(Resources res) {
    view.setRecyclerView();
    view.showLoading();

    String query1 = res.getString(R.string.acupoints);
    String query2 = res.getString(R.string.acupoints_head);
    String query3 = res.getString(R.string.acupoints_whole_body);
    compositeDisposable.add(Single.merge(getImages(query1), getImages(query2), getImages(query3))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(documents -> {
          adapterDataModel.addAll(documents);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.hideLoading();
        }));
  }

  private Single<List<Documents>> getImages(String query) {
    return imagesNetwork.getImages(query)
        .subscribeOn(Schedulers.io())
        .map(dResult -> dResult.getDocuments());
  }

  @Override public void onDetach() {
    compositeDisposable.clear();
  }

  @Override public void onItemClick(Documents item) {
    view.showImageViewDialog(item.getImage_url());
  }

  @Override public void onDocUrlItemClick(Documents item) {
    view.showWebViewDialog(item.getDoc_url());
  }

}