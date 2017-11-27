package io.github.ovso.massage.f_symptom;

import android.support.annotation.StringRes;

/**
 * Created by jaeho on 2017. 11. 27..
 */

public interface SymptomPresenter {

  void onActivityCreate();

  void onDetach();

  interface View {

    void setRecyclerView();

    void showMessage(@StringRes int resId);

    void refresh();
  }
}
