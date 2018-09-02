package io.github.ovso.massage.main.f_symptom;

import android.content.ActivityNotFoundException;
import android.text.TextUtils;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.Constants;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.VideoMode;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.main.f_symptom.db.SymptomLocalDb;
import io.github.ovso.massage.main.f_symptom.db.SymptomRo;
import io.github.ovso.massage.main.f_symptom.model.Symptom;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class SymptomPresenterImpl extends Exception implements SymptomPresenter {
  private SymptomPresenter.View view;
  private DatabaseReference databaseReference;
  private CompositeDisposable compositeDisposable;
  private BaseAdapterDataModel<SelectableItem<Symptom>> adapterDataModel;
  private SymptomLocalDb localDb;

  public SymptomPresenterImpl(SymptomPresenter.View view,
      BaseAdapterDataModel<SelectableItem<Symptom>> adapterDataModel,
      DatabaseReference databaseReference, SymptomLocalDb localDb,
      CompositeDisposable compositeDisposable) {
    this.view = view;
    this.adapterDataModel = adapterDataModel;
    this.databaseReference = databaseReference;
    this.localDb = localDb;
    this.compositeDisposable = compositeDisposable;
  }

  @Override public void onActivityCreate() {
    view.setRecyclerView();
    view.showLoading();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .map(dataSnapshot -> {
          final List<SelectableItem<Symptom>> items = new ArrayList<>();
          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Symptom symptom = snapshot.getValue(Symptom.class);
            ArrayList<SymptomRo> symptomRos = localDb.getAll();
            boolean isFavorite = false;
            for (int i = 0; i < localDb.getSize(); i++) {
              SymptomRo symptomRo = symptomRos.get(i);
              if (symptom.getId() == symptomRo.getId()) {
                isFavorite = true;
                break;
              }
            }
            items.add(new SelectableItem<Symptom>().setFavorite(isFavorite).setItem(symptom));
          }
          if (localDb.getSize() > 0) {
            localDb.sort(items);
          }
          return items;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.hideLoading();
        }));
  }

  @DebugLog @Override public void onItemClick(SelectableItem<Symptom> selectableItem) {
    if (!TextUtils.isEmpty(selectableItem.getItem().getUrl())) {
      view.showWebViewDialog(selectableItem.getItem());
    }
  }

  @Override public void onVideoClick(int position, SelectableItem<Symptom> item) {
    String video_id = item.getItem().getVideo_id();
    if (!TextUtils.isEmpty(video_id)) {
      view.showVideoTypeDialog((dialog, which) -> {
        Timber.d("which = " + which);
        try {
          dialog.dismiss();
          switch (VideoMode.fromWhich(which)) {
            case PORTRAIT:
              view.showPortraitVideo(video_id);
              break;
            case LANDSCAPE:
              view.showLandscapeVideo(video_id);
              break;
            case CANCEL:
              dialog.dismiss();
              break;
          }
        } catch (ActivityNotFoundException e) {
          e.printStackTrace();
          view.showYoutubeUseWarningDialog();
        }
      });
    } else {
      view.showMessage(R.string.video_does_not_exist);
    }
  }

  @Override public void onDestroyView() {
    compositeDisposable.clear();
  }

  @Override public void onFavoriteClick(final SelectableItem<Symptom> selectableItem) {
    view.showLoading();
    view.removeRefresh();
    adapterDataModel.clear();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .map(dataSnapshot -> {
          final int itemId = selectableItem.getItem().getId();
          if (selectableItem.isFavorite()) {
            localDb.delete(itemId);
          } else {
            localDb.add(itemId);
          }
          final List<SelectableItem<Symptom>> items = new ArrayList<>();
          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Symptom symptom = snapshot.getValue(Symptom.class);
            boolean isFavorite = false;
            for (int i = 0; i < localDb.getSize(); i++) {
              int uniqueId = localDb.get(i).getId();
              if (symptom.getId() == uniqueId) {
                isFavorite = true;
                break;
              }
            }
            items.add(new SelectableItem<Symptom>().setItem(symptom).setFavorite(isFavorite));
          }
          if (localDb.getSize() > 0) {
            localDb.sort(items);
          }
          return items;
        })
        .delay(Constants.DELAY, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.hideLoading();
        }));
  }
}