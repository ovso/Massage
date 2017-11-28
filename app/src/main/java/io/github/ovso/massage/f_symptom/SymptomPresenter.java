package io.github.ovso.massage.f_symptom;

import android.support.annotation.StringRes;
import io.github.ovso.massage.f_symptom.model.Symptom;

/**
 * Created by jaeho on 2017. 11. 27..
 */

public interface SymptomPresenter {

  void onActivityCreate();

  void onDetach();

  void onItemClick(Symptom item);

  void onItemClick(int position, Symptom item);

  void onRecommendClick(int position, Symptom item);

  interface View {

    void setRecyclerView();

    void showMessage(@StringRes int resId);

    void refresh();

    void refresh(int position);
  }
}
