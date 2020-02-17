package io.github.ovso.massage.main.f_theme;

import android.content.ActivityNotFoundException;
import android.text.TextUtils;

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.VideoMode;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.main.f_theme.model.Theme;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ThemePresenterImpl implements ThemePresenter {
    private View view;
    private DatabaseReference databaseReference;
    private CompositeDisposable compositeDisposable;
    private BaseAdapterDataModel<SelectableItem<Theme>> adapterDataModel;

    public ThemePresenterImpl(View view,
                              BaseAdapterDataModel<SelectableItem<Theme>> adapterDataModel,
                              DatabaseReference databaseReference,
                              CompositeDisposable compositeDisposable) {
        this.view = view;
        this.adapterDataModel = adapterDataModel;
        this.databaseReference = databaseReference;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onActivityCreate() {
        view.setRecyclerView();
        view.showLoading();
        compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
                .subscribeOn(Schedulers.io())
                .map(dataSnapshot -> {
                    final List<SelectableItem<Theme>> items = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Theme Theme = snapshot.getValue(Theme.class);
                        items.add(new SelectableItem<Theme>().setItem(Theme));
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

    @DebugLog
    @Override
    public void onItemClick(SelectableItem<Theme> selectableItem) {
        onVideoClick(0, selectableItem);
    }

    @Override
    public void onVideoClick(int position, SelectableItem<Theme> item) {
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

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
    }

}