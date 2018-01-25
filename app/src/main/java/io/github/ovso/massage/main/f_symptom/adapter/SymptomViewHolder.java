package io.github.ovso.massage.main.f_symptom.adapter;

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
  @BindView(R.id.video_imageview) ImageView videoImageView;
  @BindView(R.id.rec_imageview) ImageView recImageView;
  @BindView(R.id.rec_textview) TextView recTextView;
  @BindView(R.id.fav_imageview) ImageView favImageView;

  public SymptomViewHolder(View itemView) {
    super(itemView);
  }
}