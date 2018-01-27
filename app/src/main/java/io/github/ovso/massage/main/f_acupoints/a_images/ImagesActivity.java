package io.github.ovso.massage.main.f_acupoints.a_images;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import butterknife.BindView;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.Constants;
import io.github.ovso.massage.framework.adapter.BaseAdapterView;
import io.github.ovso.massage.framework.customview.BaseActivity;
import io.github.ovso.massage.framework.listener.OnRecyclerItemClickListener;
import io.github.ovso.massage.main.f_acupoints.a_images.adapter.ImagesAdapter;
import io.github.ovso.massage.main.f_acupoints.model.Documents;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import lombok.Getter;

/**
 * Created by jaeho on 2018. 1. 25
 */

public class ImagesActivity extends BaseActivity
    implements ImagesPresenter.View, OnRecyclerItemClickListener<Documents> {
  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  @Inject @Getter CompositeDisposable compositeDisposable;
  @Inject @Getter ImagesAdapter adapter;
  @Inject BaseAdapterView adapterView;
  @Inject ImagesPresenter presenter;

  @Override protected int getLayoutResId() {
    return R.layout.activity_images2;
  }

  @Override protected boolean isDagger() {
    return true;
  }

  @Override protected void onCreated(Bundle savedInstanceState) {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    presenter.onCreated(getIntent());
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return true;
  }

  @Override public void setRecyclerView() {
    GridLayoutManager layout = new GridLayoutManager(getApplicationContext(), 2);
    recyclerView.setLayoutManager(layout);
    recyclerView.getItemAnimator().setChangeDuration(Constants.DURATION_RECYCLERVIEW_ANI);
    recyclerView.setAdapter(adapter);
  }

  @Override public void refresh() {
    adapter.refresh();
  }

  @DebugLog @Override public void onItemClick(Documents item) {

  }

  @Override protected void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }
}
