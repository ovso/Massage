package io.github.ovso.massage.f_symptom;

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.f_symptom.model.Symptom;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class SymptomPresenterImpl implements SymptomPresenter {

  private SymptomPresenter.View view;
  private DatabaseReference databaseReference;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private BaseAdapterDataModel<Symptom> adapterDataModel;

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
          List<Symptom> items = new ArrayList<>();
          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Symptom symptom = snapshot.getValue(Symptom.class);
            items.add(symptom);
          }
          adapterDataModel.addAll(items);
          view.refresh();
        }, throwable -> view.showMessage(R.string.error_server)));
  }

  @Override public void onDetach() {
    compositeDisposable.clear();
  }

  @Override public void onItemClick(Symptom item) {
    Map<String, Object> map = new HashMap<>();
    int rec = item.getRec() + 1;
    map.put("0/rec", rec);
    item.setRec(rec);
    adapterDataModel.add(item);
    view.refresh(0);

    databaseReference.updateChildren(map, (databaseError, databaseReference) -> {
    });
  }

  @DebugLog @Override public void onItemClick(int position, Symptom item) {

  }

  @Override public void onRecommendClick(int position, Symptom item) {
    Map<String, Object> map = new HashMap<>();
    int rec = item.getRec() + 1;
    map.put(position + "/rec", rec);
    item.setRec(rec);
    adapterDataModel.add(item);
    view.refresh(position);

    compositeDisposable.add(RxFirebaseDatabase.updateChildren(databaseReference, map)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> Timber.d("successe"),
            throwable -> view.showMessage(R.string.error_server)));
  }
}
