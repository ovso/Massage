package io.github.ovso.massage.main.f_symptom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.main.base.WebviewAlertDialog;
import io.github.ovso.massage.framework.Constants;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.customview.BaseFragment;
import io.github.ovso.massage.framework.listener.OnCustomRecyclerItemClickListener;
import io.github.ovso.massage.main.f_symptom.adapter.SymptomAdapter;
import io.github.ovso.massage.main.f_symptom.adapter.SymptomAdapterView;
import io.github.ovso.massage.main.f_symptom.model.Symptom;
import io.github.ovso.massage.view.ui.player.LandscapeVideoActivity;
import io.github.ovso.massage.view.ui.player.PortraitVideoActivity;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

public class SymptomFragment extends BaseFragment
        implements SymptomPresenter.View, OnCustomRecyclerItemClickListener<SelectableItem<Symptom>> {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @Inject
    SymptomAdapter adapter;
    @Inject
    SymptomAdapterView adapterView;
    @Inject
    SymptomPresenter presenter;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_symptom;
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        presenter.onActivityCreate();
    }

    @Override
    protected boolean isDagger() {
        return true;
    }

    public static SymptomFragment newInstance() {
        return new SymptomFragment();
    }

    @Override
    public void setRecyclerView() {
        if (recyclerView.getItemAnimator() != null) {
            recyclerView.getItemAnimator().setChangeDuration(Constants.DURATION_RECYCLERVIEW_ANI);
            recyclerView.getItemAnimator().setRemoveDuration(Constants.DURATION_RECYCLERVIEW_ANI);
        }
        recyclerView.setItemAnimator(new SlideInDownAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshRemove(int position) {
        adapterView.refreshRemove(position);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showYoutubeUseWarningDialog() {
        new AlertDialog.Builder(getActivity()).setMessage(R.string.youtube_use_warning)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void refresh() {
        adapterView.refresh();
    }

    @Override
    public void refresh(int position) {
        adapterView.refresh(position);
    }

    @Override
    public void showPortraitVideo(String videoId) {
        Intent intent = new Intent(getContext(), PortraitVideoActivity.class);
        intent.putExtra("video_id", videoId);
        startActivity(intent);
    }

    @Override
    public void showLandscapeVideo(String videoId) {
        Intent intent = new Intent(getContext(), LandscapeVideoActivity.class);
        intent.putExtra("video_id", videoId);
        startActivity(intent);
    }

    @Override
    public void showVideoTypeDialog(DialogInterface.OnClickListener $onClickListener) {
        final DialogInterface.OnClickListener onClickListener =
                (dialog, which) -> $onClickListener.onClick(dialog, which);
        new AlertDialog.Builder(getContext()).setMessage(R.string.please_select_the_player_mode)
                .setPositiveButton(R.string.portrait_mode,
                        onClickListener)
                .setNeutralButton(R.string.landscape_mode, onClickListener)
                .setNegativeButton(android.R.string.cancel, onClickListener)
                .show();
    }

    @Override
    public void showWebViewDialog(Symptom item) {
        if (getFragmentManager() != null) {
            WebviewAlertDialog d = new WebviewAlertDialog();
            d.url = item.url;
            d.flag = item.flag;
            d.show(getFragmentManager(), WebviewAlertDialog.class.getSimpleName());
        }
    }

    @Override
    public void removeRefresh() {
        adapterView.refreshRemove();
    }

    @Override
    public void onItemClick(SelectableItem<Symptom> item) {
        presenter.onItemClick(item);
    }

    @Override
    public void onVideoClick(int position, SelectableItem<Symptom> item) {
        presenter.onVideoClick(position, item);
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroyView();
        super.onDestroyView();
    }
}