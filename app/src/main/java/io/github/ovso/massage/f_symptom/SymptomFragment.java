package io.github.ovso.massage.f_symptom;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseFragment;
import javax.inject.Inject;

/**
 * Created by jaeho on 2017. 10. 20
 */

public class SymptomFragment extends BaseFragment implements SymptomPresenter.View {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;

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
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

  }
}