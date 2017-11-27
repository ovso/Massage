package io.github.ovso.massage.f_symptom;

/**
 * Created by jaeho on 2017. 11. 27..
 */

public interface SymptomPresenter {

  void onActivityCreate();

  interface View {

    void setRecyclerView();
  }
}
