package io.github.ovso.massage.f_acupoints;

import android.content.ActivityNotFoundException;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.f_acupoints.adapter.AcupointsAdapter;
import io.github.ovso.massage.f_acupoints.db.AcupointsLocalDb;
import io.github.ovso.massage.f_acupoints.db.AcupointsRo;
import io.github.ovso.massage.f_acupoints.model.Acupoints;
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
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class AcupointsPresenterImpl implements AcupointsPresenter {
  private View view;
  private DatabaseReference databaseReference;
  private CompositeDisposable compositeDisposable;
  private BaseAdapterDataModel<SelectableItem<Acupoints>> adapterDataModel;
  private AcupointsLocalDb localDb;

  public AcupointsPresenterImpl(View view, BaseAdapterDataModel adapterDataModel,
      DatabaseReference databaseReference, AcupointsLocalDb localDb,
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
          List<SelectableItem<Acupoints>> items = new ArrayList<>();
          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Acupoints Acupoints = snapshot.getValue(Acupoints.class);
            ArrayList<AcupointsRo> AcupointsRos = localDb.getAll();
            boolean isFavorite = false;
            for (int i = 0; i < localDb.getSize(); i++) {
              AcupointsRo AcupointsRo = AcupointsRos.get(i);
              if (Acupoints.getId() == AcupointsRo.getId()) {
                isFavorite = true;
                break;
              }
            }
            items.add(new SelectableItem<Acupoints>().setFavorite(isFavorite).setItem(Acupoints));
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

  @DebugLog @Override public void onItemClick(SelectableItem<Acupoints> item) {
    switch (item.getItem().getType()) {
      case AcupointsAdapter.TYPE_SITE:
        view.showWebViewDialog(item.getItem().getUrl());
        break;
      case AcupointsAdapter.TYPE_VIDEO:
        try {
          view.showVideo(item.getItem().getUrl());
        } catch (ActivityNotFoundException e) {
          e.printStackTrace();
          view.showYoutubeUseWarningDialog();
        }

        break;
    }
  }

  @Override public void onRecommendClick(final int position, final SelectableItem<Acupoints> $item) {
    view.showLoading();
    databaseReference.runTransaction(new Transaction.Handler() {
      @Override public Transaction.Result doTransaction(MutableData mutableData) {
        ArrayList<Object> objects = (ArrayList<Object>) mutableData.getValue();
        if (!ObjectUtils.isEmpty(objects)) {
          HashMap<String, Object> objectHashMap =
              (HashMap<String, Object>) objects.get($item.getItem().getId());
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
            if (i == $item.getItem().getId()) {
              Acupoints Acupoints = dataSnapshots.get(i).getValue(Acupoints.class);
              $item.getItem().setRec(Acupoints.getRec());
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

  @Override public void onFavoriteClick(int position, SelectableItem<Acupoints> $item) {
    view.showLoading();
    if ($item.isFavorite()) {
      localDb.delete($item.getItem().getId());
    } else {
      localDb.add($item.getItem().getId());
    }
    Timber.d("position = " + position);
    Timber.d("realm size = " + localDb.getSize());
    view.removeRefresh();
    adapterDataModel.clear();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .delay(Constants.DELAY, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dataSnapshot -> {
          List<SelectableItem<Acupoints>> items = new ArrayList<>();
          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Acupoints Acupoints = snapshot.getValue(Acupoints.class);

            boolean isFavorite = false;

            for (int i = 0; i < localDb.getSize(); i++) {
              int uniqueId = localDb.get(i).getId();
              if (Acupoints.getId() == uniqueId) {
                isFavorite = true;
                break;
              }
            }
            items.add(new SelectableItem<Acupoints>().setItem(Acupoints).setFavorite(isFavorite));
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