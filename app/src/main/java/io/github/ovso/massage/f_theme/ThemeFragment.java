package io.github.ovso.massage.f_theme;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.common.WebviewAlertDialog;
import io.github.ovso.massage.f_theme.adapter.ThemeAdapter;
import io.github.ovso.massage.f_theme.adapter.ThemeAdapterView;
import io.github.ovso.massage.f_theme.model.Theme;
import io.github.ovso.massage.framework.Constants;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.customview.BaseFragment;
import io.github.ovso.massage.framework.listener.OnCustomRecyclerItemClickListener;
import io.github.ovso.massage.youtube.FullscreenVideoActivity;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import lombok.Getter;

/**
 * Created by jaeho on 2017. 10. 20
 */

public class ThemeFragment extends BaseFragment
    implements ThemePresenter.View, OnCustomRecyclerItemClickListener<SelectableItem<Theme>> {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  @BindView(R.id.root_view) View rootView;
  @BindView(R.id.progressbar) ProgressBar progressBar;
  @Inject @Getter CompositeDisposable compositeDisposable;
  @Inject @Getter ThemeAdapter adapter;
  @Inject @Getter ThemeAdapterView adapterView;
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
    ThemeFragment f = new ThemeFragment();
    return f;
  }

  @Override public void showYoutubeUseWarningDialog() {
    new AlertDialog.Builder(getActivity()).setMessage(R.string.youtube_use_warning)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }

  @Override public void showLandscapeVideo(String videoId) {
    Intent intent = new Intent(getContext(), FullscreenVideoActivity.class);
    intent.putExtra("video_id", videoId);
    startActivity(intent);
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
    if (progressBar != null) {
      progressBar.setVisibility(View.VISIBLE);
    }
  }

  @Override public void hideLoading() {
    if (progressBar != null) {
      progressBar.setVisibility(View.GONE);
    }
  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  @Override public void refresh(int position) {
    adapterView.refresh(position);
  }

  @Override public void showVideo(String videoId) {
    int startTimeMillis = 0;
    boolean autoPlay = true;
    boolean lightboxMode = true;

    Intent intent =
        YouTubeStandalonePlayer.createVideoIntent(getActivity(), Constants.DEVELOPER_KEY, videoId,
            startTimeMillis, autoPlay, lightboxMode);
    startActivity(intent);
  }

  @Override public void showWebViewDialog(Theme item) {
    new WebviewAlertDialog().setUrl(item.getUrl())
        .setFlag(item.isFlag())
        .show(getFragmentManager(), WebviewAlertDialog.class.getSimpleName());
  }

  @Override public void removeRefresh() {
    adapterView.refreshRemove();
  }

  @DebugLog @Override public void onDetach() {
    super.onDetach();
    presenter.onDetach();
  }

  @DebugLog @Override public void onItemClick(SelectableItem<Theme> item) {
    presenter.onItemClick(item);
  }

  @Override public void onRecommendClick(int position, SelectableItem<Theme> item) {
    presenter.onRecommendClick(position, item);
  }

  @Override public void onFavoriteClick(int position, SelectableItem<Theme> item) {
    presenter.onFavoriteClick(position, item);
  }

  @Override public void onVideoClick(int position, SelectableItem<Theme> item) {
    presenter.onVideoClick(position, item);
  }

  @Override public void onItemLongClick(SelectableItem<Theme> item) {
    presenter.onVideoLongClick(item);
  }
}