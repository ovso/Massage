package io.github.ovso.massage.main.f_symptom;

import android.content.ActivityNotFoundException;
import android.text.TextUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.VideoMode;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.main.f_symptom.model.Symptom;
import io.github.ovso.massage.utils.ResourceProvider;
import io.github.ovso.massage.utils.SchedulerProvider;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class SymptomPresenterImpl extends Exception implements SymptomPresenter {
    private SymptomPresenter.View view;
    private DatabaseReference databaseReference;
    private CompositeDisposable compositeDisposable;
    private BaseAdapterDataModel<SelectableItem<Symptom>> adapterDataModel;

    public SymptomPresenterImpl(
            View view,
            BaseAdapterDataModel<SelectableItem<Symptom>> adapterDataModel,
            DatabaseReference databaseReference,
            CompositeDisposable compositeDisposable
    ) {
        this.view = view;
        this.adapterDataModel = adapterDataModel;
        this.databaseReference = databaseReference;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onActivityCreate() {
        view.setRecyclerView();
        view.showLoading();
        reqItems();
    }

    private void reqItems() {
        Disposable subscribe = Single.fromCallable(() -> {
            String jsonString = ResourceProvider.INSTANCE.fromAssets("symptom.json");
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
            JsonArray asJsonArray = jsonElement.getAsJsonArray();
            final List<SelectableItem<Symptom>> items = new ArrayList<>();
            for (JsonElement element : asJsonArray) {
                Symptom symptom = gson.fromJson(element.toString(), Symptom.class);
                SelectableItem<Symptom> symptomSelectableItem = new SelectableItem<>();
                symptomSelectableItem.item = symptom;
                items.add(symptomSelectableItem);
            }
            return items;
        })
                .subscribeOn(SchedulerProvider.INSTANCE.io())
                .observeOn(SchedulerProvider.INSTANCE.ui())
                .subscribe(
                        items -> {
                            adapterDataModel.addAll(items);
                            view.refresh();
                            view.hideLoading();
                        },
                        throwable -> {
                        }
                );
        compositeDisposable.add(subscribe);
    }

    @DebugLog
    @Override
    public void onItemClick(SelectableItem<Symptom> selectableItem) {
        String video_id = selectableItem.item.video_id;
        if (!TextUtils.isEmpty(video_id)) {
            view.showVideoTypeDialog((dialog, which) -> {
                Timber.d("which = %s", which);
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

    @Override
    public void onVideoClick(int position, SelectableItem<Symptom> item) {
        view.showPortraitVideo(item.item.video_id);
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
    }
}