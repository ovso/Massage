package io.github.ovso.massage.main.f_theme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.main.base.WebviewAlertDialog;
import io.github.ovso.massage.framework.Constants;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.customview.BaseFragment;
import io.github.ovso.massage.framework.listener.OnCustomRecyclerItemClickListener;
import io.github.ovso.massage.main.f_theme.adapter.ThemeAdapter;
import io.github.ovso.massage.main.f_theme.adapter.ThemeAdapterView;
import io.github.ovso.massage.main.f_theme.model.Theme;
import io.github.ovso.massage.youtube.LandscapeVideoActivity;
import io.github.ovso.massage.youtube.PortraitVideoActivity;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

public class ThemeFragment extends BaseFragment
    implements ThemePresenter.View, OnCustomRecyclerItemClickListener<SelectableItem<Theme>> {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  @BindView(R.id.root_view) View rootView;
  @BindView(R.id.progressbar) ProgressBar progressBar;
  @Inject CompositeDisposable compositeDisposable;
  @Inject ThemeAdapter adapter;
  @Inject ThemeAdapterView adapterView;
  @Inject ThemePresenter presenter;

  @Override protected int getLayoutResID() {
    return R.layout.fragment_theme;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {
    presenter.onActivityCreate();
  }

  @Override protected boolean isDagger() {
    return true;
  }

  public static ThemeFragment newInstance() {
    return new ThemeFragment();
  }

  @Override public void showYoutubeUseWarningDialog() {
    new AlertDialog.Builder(getActivity()).setMessage(R.string.youtube_use_warning)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }

  @Override public void showLandscapeVideo(String videoId) {
    Intent intent = new Intent(getContext(), LandscapeVideoActivity.class);
    intent.putExtra("video_id", videoId);
    startActivity(intent);
  }

  @Override public void showVideoTypeDialog(DialogInterface.OnClickListener $onClickListener) {
    final DialogInterface.OnClickListener onClickListener =
        $onClickListener::onClick;
    new AlertDialog.Builder(getContext()).setMessage(R.string.please_select_the_player_mode)
        .setPositiveButton(R.string.portrait_mode,
            onClickListener)
        .setNeutralButton(R.string.landscape_mode, onClickListener)
        .setNegativeButton(android.R.string.cancel, onClickListener)
        .show();
  }

  @Override public void setRecyclerView() {
    recyclerView.getItemAnimator().setChangeDuration(Constants.DURATION_RECYCLERVIEW_ANI);
    recyclerView.getItemAnimator().setRemoveDuration(Constants.DURATION_RECYCLERVIEW_ANI);
    recyclerView.setItemAnimator(new SlideInDownAnimator());
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);
  }

  @Override public void showMessage(int resId) {
    Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
  }

  @Override public void showMessage(String msg) {
    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
  }

  @Override public void refreshRemove(int position) {
    adapterView.refreshRemove(position);
  }

  @Override public void showLoading() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  @Override public void refresh(int position) {
    adapterView.refresh(position);
  }

  @Override public void showPortraitVideo(String videoId) {
    Intent intent = new Intent(getContext(), PortraitVideoActivity.class);
    intent.putExtra("video_id", videoId);
    startActivity(intent);
  }

  @Override public void showWebViewDialog(Theme item) {
    new WebviewAlertDialog().setUrl(item.getUrl())
        .setFlag(item.isFlag())
        .show(getChildFragmentManager(), WebviewAlertDialog.class.getSimpleName());
  }

  @Override public void removeRefresh() {
    adapterView.refreshRemove();
  }

  @DebugLog @Override public void onItemClick(SelectableItem<Theme> item) {
    presenter.onItemClick(item);
  }

  @Override public void onVideoClick(int position, SelectableItem<Theme> item) {
    presenter.onVideoClick(position, item);
  }

  @Override public void onDestroyView() {
    presenter.onDestroyView();
    super.onDestroyView();
  }
}