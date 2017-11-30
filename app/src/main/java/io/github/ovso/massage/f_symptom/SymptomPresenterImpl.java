package io.github.ovso.massage.f_symptom;

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.f_symptom.adapter.SymptomAdapter;
import io.github.ovso.massage.f_symptom.dao.SymptomFav;
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
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class SymptomPresenterImpl implements SymptomPresenter {
  private SymptomPresenter.View view;
  private DatabaseReference databaseReference;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private BaseAdapterDataModel<SelectableItem<Symptom>> adapterDataModel;
  private SymptomLocalDb localDb;

  public SymptomPresenterImpl(SymptomPresenter.View view, BaseAdapterDataModel adapterDataModel,
      DatabaseReference databaseReference, SymptomLocalDb localDb) {
    this.view = view;
    this.adapterDataModel = adapterDataModel;
    this.databaseReference = databaseReference;
    this.localDb = localDb;
  }

  @Override public void onActivityCreate() {
    view.setRecyclerView();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dataSnapshot -> {
          List<SelectableItem<Symptom>> items = new ArrayList<>();
          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Symptom symptom = snapshot.getValue(Symptom.class);
            ArrayList<SymptomFav> symptomFavs = localDb.getAll();
            boolean isFavorite = false;
            for (int i = 0; i < localDb.getSize(); i++) {
              SymptomFav symptomFav = symptomFavs.get(i);
              if (symptom.getId() == symptomFav.getId()) {
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
        }, throwable -> view.showMessage(R.string.error_server)));
  }

  @Override public void onDetach() {
    compositeDisposable.clear();
  }

  @DebugLog @Override public void onItemClick(SelectableItem<Symptom> item) {
    switch (item.getItem().getType()) {
      case SymptomAdapter.TYPE_SITE:
        view.showWebViewDialog(item.getItem().getUrl());
        break;
      case SymptomAdapter.TYPE_VIDEO:
        view.showVideo(item.getItem().getUrl());
        break;
    }
  }

  @Override public void onRecommendClick(final int position, final SelectableItem<Symptom> $item) {

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
              Symptom symptom = dataSnapshots.get(i).getValue(Symptom.class);
              $item.getItem().setRec(symptom.getRec());
              view.refresh(position);
              break;
            }
          }
          view.showMessage(R.string.you_recommended_it);
        } else {
          view.showMessage(R.string.error_server);
        }
      }
    });
  }

  @Override public void onFavoriteClick(int position, SelectableItem<Symptom> $item) {
    if ($item.isFavorite()) {
      SymptomFav symptomFav = localDb.find("id", $item.getItem().getId());
      if (!ObjectUtils.isEmpty(symptomFav)) {
        localDb.delete(symptomFav);
      }
    } else {
      localDb.add($item.getItem().getId());
    }

    Timber.d("realm size = " + localDb.getSize());
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
        }, throwable -> view.showMessage(R.string.error_server)));
  }
}