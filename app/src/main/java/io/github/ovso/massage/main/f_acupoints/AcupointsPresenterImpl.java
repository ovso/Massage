package io.github.ovso.massage.main.f_acupoints;

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
import io.github.ovso.massage.framework.Constants;
import io.github.ovso.massage.framework.ObjectUtils;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.main.f_acupoints.a_images.ImagesNetwork;
import io.github.ovso.massage.main.f_acupoints.db.AcupointsLocalDb;
import io.github.ovso.massage.main.f_acupoints.db.AcupointsRo;
import io.github.ovso.massage.main.f_acupoints.model.Acupoints;
import io.github.ovso.massage.main.f_acupoints.model.DResult;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class AcupointsPresenterImpl implements AcupointsPresenter {
  private View view;
  private DatabaseReference databaseReference;
  private CompositeDisposable compositeDisposable;
  private BaseAdapterDataModel<SelectableItem<Acupoints>> adapterDataModel;
  private AcupointsLocalDb localDb;
  private ImagesNetwork imagesNetwork;

  public AcupointsPresenterImpl(View view, BaseAdapterDataModel adapterDataModel,
      DatabaseReference databaseReference, AcupointsLocalDb localDb,
      CompositeDisposable compositeDisposable, ImagesNetwork imagesNetwork) {
    this.view = view;
    this.adapterDataModel = adapterDataModel;
    this.databaseReference = databaseReference;
    this.localDb = localDb;
    this.compositeDisposable = compositeDisposable;
    this.imagesNetwork = imagesNetwork;
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

  @Override public void onItemClick(SelectableItem<Acupoints> selectableItem) {
    /*
    if (!TextUtils.isEmpty(selectableItem.getItem().getUrl())) {
      view.showWebViewDialog(selectableItem.getItem());
    }
    */

    compositeDisposable.add(imagesNetwork.getImages(selectableItem.getItem().getTitle())
        .subscribeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<DResult>() {
          @DebugLog @Override public void accept(DResult result) throws Exception {
            view.navigateToImages(result.getDocuments());
          }
        }, new Consumer<Throwable>() {
          @DebugLog @Override public void accept(Throwable throwable) throws Exception {
            view.showMessage(R.string.error_server);
          }
        }));
  }

  @Override public void onVideoClick(int position, SelectableItem<Acupoints> item) {
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

  @Override public void onVideoLongClick(SelectableItem<Acupoints> item) {
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
  public void onRecommendClick(final int position, final SelectableItem<Acupoints> selectableItem) {
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
              Acupoints acupoints = dataSnapshots.get(i).getValue(Acupoints.class);
              selectableItem.setItem(acupoints);
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