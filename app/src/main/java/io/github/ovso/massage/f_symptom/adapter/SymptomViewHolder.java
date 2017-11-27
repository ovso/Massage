package io.github.ovso.massage.f_symptom.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class SymptomViewHolder extends BaseRecyclerAdapter.BaseViewHolder {

  @BindView(R.id.title_textview) TextView titleTextview;
  @BindView(R.id.imageview) ImageView imageview;
  @BindView(R.id.views_textview) TextView viewsTextview;

  public SymptomViewHolder(View itemView) {
    super(itemView);
  }
}
