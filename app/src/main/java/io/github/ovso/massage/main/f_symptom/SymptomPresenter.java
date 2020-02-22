package io.github.ovso.massage.main.f_symptom;

import android.content.DialogInterface;

import androidx.annotation.StringRes;

import io.github.ovso.massage.main.f_symptom.model.Symptom;
import io.github.ovso.massage.framework.SelectableItem;

public interface SymptomPresenter {

    void onActivityCreate();

    void onItemClick(SelectableItem<Symptom> item);

    void onVideoClick(int position, SelectableItem<Symptom> item);

    void onDestroyView();

    interface View {

        void setRecyclerView();

        void showMessage(@StringRes int resId);

        void refresh();

        void refresh(int position);

        void showPortraitVideo(String videoId);

        void showWebViewDialog(Symptom item);

        void removeRefresh();

        void showMessage(String msg);

        void refreshRemove(int position);

        void showLoading();

        void hideLoading();

        void showYoutubeUseWarningDialog();

        void showLandscapeVideo(String videoId);

        void showVideoTypeDialog(DialogInterface.OnClickListener onClickListener);
    }
}
