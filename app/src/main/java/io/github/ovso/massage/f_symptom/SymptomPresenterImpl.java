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
import io.github.ovso.massage.f_symptom.model.Symptom;
import io.github.ovso.massage.framework.Constants;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
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

  public SymptomPresenterImpl(SymptomPresenter.View view, BaseAdapterDataModel adapterDataModel,
      DatabaseReference databaseReference) {
    this.view = view;
    this.adapterDataModel = adapterDataModel;
    this.databaseReference = databaseReference;
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
            items.add(new SelectableItem<Symptom>().setFavorite(false).setItem(symptom));
          }
          adapterDataModel.addAll(items);
          view.refresh();
        }, throwable -> view.showMessage(R.string.error_server)));
  }

  @Override public void onDetach() {
    compositeDisposable.clear();
  }

  @Override public void onItemClick(SelectableItem<Symptom> item) {

  }

  @DebugLog @Override public void onItemClick(int position, SelectableItem<Symptom> item) {
    switch (item.getItem().getType()) {
      case SymptomAdapter.TYPE_SITE:
        view.showWebViewDialog(item.getItem().getUrl());
        break;
      case SymptomAdapter.TYPE_VIDEO:
        view.showVideo(item.getItem().getUrl());
        break;
    }
  }

  @Override public void onRecommendClick(int position, SelectableItem<Symptom> $item) {

    databaseReference.runTransaction(new Transaction.Handler() {
      @DebugLog @Override public Transaction.Result doTransaction(MutableData mutableData) {

        ArrayList<Object> o = (ArrayList<Object>) mutableData.getValue();
        HashMap<String, Object> o2 = (HashMap<String, Object>) o.get($item.getItem().getId());
        long l = (long) o2.get("rec");
        l = l + 1;
        o2.put("rec", l);
        mutableData.setValue(o);
        return Transaction.success(mutableData);
      }

      @DebugLog @Override
      public void onComplete(DatabaseError error, boolean committed, DataSnapshot dataSnapshot) {
        if (committed) {
          ArrayList<DataSnapshot> dataSnapshots = Lists.newArrayList(dataSnapshot.getChildren());
          int size = dataSnapshots.size();
          for (int i = 0; i < size; i++) { // i : 서버 정렬
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

    //view.showMessage("추천하셨습니다.");

    /*
    Map<String, Object> map = new HashMap<>();
    int rec = item.getItem().getRec() + 1;
    map.put(item.getItem().getId() + "/rec", rec);
    item.getItem().setRec(rec);
    adapterDataModel.add(item);
    view.refresh(position);

    compositeDisposable.add(RxFirebaseDatabase.updateChildren(databaseReference, map)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> Timber.d("successe"),
            throwable -> view.showMessage(R.string.error_server)));
    */
  }

  private ArrayList<Integer> ids = new ArrayList<>();

  @Override public void onFavoriteClick(int position, SelectableItem<Symptom> $item) {

    if ($item.isFavorite()) {
      int idsIndex = ids.indexOf($item.getItem().getId());
      if (idsIndex != -1) {
        ids.remove(idsIndex);
      }
    } else {
      ids.add($item.getItem().getId());
    }

    Timber.d("ids sie = " + ids.size() + ", ids = " + ids);

    view.removeRefresh();
    adapterDataModel.clear();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .delay(Constants.DELAY, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dataSnapshot -> {
          List<SelectableItem<Symptom>> selectableItems = new ArrayList<>();
          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Symptom symptom = snapshot.getValue(Symptom.class);
            selectableItems.add(new SelectableItem<Symptom>().setItem(symptom).setFavorite(false));
          }

          for (int i = 0; i < selectableItems.size(); i++) {
            SelectableItem<Symptom> selectableItem = selectableItems.get(i);
            for (int i1 = 0; i1 < ids.size(); i1++) {
              int uniqueId = ids.get(i1);
              if (selectableItem.getItem().getId() == uniqueId) {
                selectableItem.setFavorite(true);
              }
            }
          }

          Collections.sort(selectableItems, (o1, o2) -> {
            int a1 = o1.isFavorite() ? 0 : 1;
            int a2 = o2.isFavorite() ? 0 : 1;
            return a1 - a2;
          });
          adapterDataModel.addAll(selectableItems);
          view.refresh();
        }, throwable -> view.showMessage(R.string.error_server)));
  }
}