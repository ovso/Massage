package io.github.ovso.massage.f_symptom;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.f_symptom.adapter.SymptomAdapter;
import io.github.ovso.massage.f_symptom.adapter.SymptomAdapterView;
import io.github.ovso.massage.f_symptom.model.Symptom;
import io.github.ovso.massage.framework.Constants;
import io.github.ovso.massage.framework.customview.BaseFragment;
import io.github.ovso.massage.framework.listener.OnRecyclerItemClickListener;
import javax.inject.Inject;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import lombok.Getter;

/**
 * Created by jaeho on 2017. 10. 20
 */

public class SymptomFragment extends BaseFragment
    implements SymptomPresenter.View, OnRecyclerItemClickListener<Symptom> {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  @Inject @Getter SymptomAdapter adapter;
  @Inject @Getter SymptomAdapterView adapterView;
  @Inject SymptomPresenter presenter;

  @Override protected int getLayoutResID() {
    return R.layout.fragment_symptom;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {
    presenter.onActivityCreate();
  }

  @Override protected boolean isDagger() {
    return true;
  }

  public static SymptomFragment newInstance() {
    SymptomFragment f = new SymptomFragment();
    return f;
  }

  @Override public void setRecyclerView() {
    recyclerView.getItemAnimator().setChangeDuration(Constants.DURATION_RECYCLERVIEW_ANI);
    recyclerView.setItemAnimator(new SlideInDownAnimator());
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);
  }

  @Override public void showMessage(int resId) {

  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  @Override public void refresh(int position) {
    adapterView.refresh(position);
  }

  @Override public void onDetach() {
    super.onDetach();
    presenter.onDetach();
  }

  @DebugLog @Override public void onItemClick(Symptom item) {
    presenter.onItemClick(item);
  }

  @DebugLog @Override public void onItemClick(int position, Symptom item) {
    presenter.onItemClick(position, item);
  }
}