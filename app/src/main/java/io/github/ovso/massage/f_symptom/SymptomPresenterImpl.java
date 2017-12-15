package io.github.ovso.massage.f_symptom;

import android.content.ActivityNotFoundException;
import android.text.TextUtils;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.f_symptom.db.SymptomLocalDb;
import io.github.ovso.massage.f_symptom.db.SymptomRo;
import io.github.ovso.massage.f_symptom.model.Symptom;
import io.github.ovso.massage.framework.Constants;
import io.github.ovso.massage.framework.ObjectUtils;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class SymptomPresenterImpl extends Exception implements SymptomPresenter {
  private SymptomPresenter.View view;
  private DatabaseReference databaseReference;
  private CompositeDisposable compositeDisposable;
  private BaseAdapterDataModel<SelectableItem<Symptom>> adapterDataModel;
  private SymptomLocalDb localDb;

  public SymptomPresenterImpl(SymptomPresenter.View view, BaseAdapterDataModel adapterDataModel,
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
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dataSnapshot -> {
          List<SelectableItem<Symptom>> items = new ArrayList<>();
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
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.hideLoading();
        }));
  }

  @Override public void onDetach() {
    compositeDisposable.clear();
  }

  @DebugLog @Override public void onItemClick(SelectableItem<Symptom> selectableItem) {
    if (!TextUtils.isEmpty(selectableItem.getItem().getUrl())) {
      view.showWebViewDialog(selectableItem.getItem());
    }
  }

  @Override public void onVideoClick(int position, SelectableItem<Symptom> item) {
    String video_id = item.getItem().getVideo_id();
    if (!TextUtils.isEmpty(video_id)) {
      try {
        view.showVideo(video_id);
      } catch (ActivityNotFoundException e) {
        e.printStackTrace();
        view.showYoutubeUseWarningDialog();
      }
    }
  }

  @Override public void onVideoLongClick(SelectableItem<Symptom> item) {
    String video_id = item.getItem().getVideo_id();
    if (!TextUtils.isEmpty(video_id)) {
      try {
        view.showLandscapeVideo(video_id);
      } catch (ActivityNotFoundException e) {
        e.printStackTrace();
        view.showYoutubeUseWarningDialog();
      }
    }
  }

  @Override
  public void onRecommendClick(final int position, final SelectableItem<Symptom> selectableItem) {
    view.showLoading();
    databaseReference.runTransaction(new Transaction.Handler() {
      @Override public Transaction.Result doTransaction(MutableData mutableData) {
        ArrayList<Object> objects = (ArrayList<Object>) mutableData.getValue();
        if (!ObjectUtils.isEmpty(objects)) {
          HashMap<String, Object> objectHashMap =
              (HashMap<String, Object>) objects.get(selectableItem.getItem().getId());
          long recommendCount = (long) objectHashMap.get("rec");
          recommendCount = recommendCount + 1;
          objectHashMap.put("rec", recommendCount);
          mutableData.setValue(objects);
          return Transaction.success(mutableData);
        }
        return Transaction.success(mutableData);
      }

      @Override
      public void onComplete(DatabaseError error, boolean committed, DataSnapshot dataSnapshot) {
        if (committed) {
          ArrayList<DataSnapshot> dataSnapshots = Lists.newArrayList(dataSnapshot.getChildren());
          int size = dataSnapshots.size();
          for (int i = 0; i < size; i++) {
            if (i == selectableItem.getItem().getId()) {
              Symptom symptom = dataSnapshots.get(i).getValue(Symptom.class);
              selectableItem.setItem(symptom);
              view.refresh(position);
              break;
            }
          }
          view.showMessage(R.string.you_recommended_it);
        } else {
          view.showMessage(R.string.error_server);
        }

        view.hideLoading();
      }
    });
  }

  @Override public void onFavoriteClick(int position, SelectableItem<Symptom> $item) {
    view.showLoading();
    if ($item.isFavorite()) {
      localDb.delete($item.getItem().getId());
    } else {
      localDb.add($item.getItem().getId());
    }
    view.removeRefresh();
    adapterDataModel.clear();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .delay(Constants.DELAY, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dataSnapshot -> {
          List<SelectableItem<Symptom>> items = new ArrayList<>();
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
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.hideLoading();
        }));
  }
}