package io.github.ovso.massage.f_symptom;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class SymptomPresenterImpl implements SymptomPresenter {

  private SymptomPresenter.View view;

  public SymptomPresenterImpl(SymptomPresenter.View view) {
    this.view = view;
  }

  @Override public void onActivityCreate() {
    view.setRecyclerView();
  }
}