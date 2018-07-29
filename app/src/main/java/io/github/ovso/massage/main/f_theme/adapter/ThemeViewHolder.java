package io.github.ovso.massage.main.f_theme.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class ThemeViewHolder extends BaseRecyclerAdapter.BaseViewHolder {

  @BindView(R.id.title_textview) TextView titleTextview;
  @BindView(R.id.video_button) ImageView videoImageView;
  @BindView(R.id.rec_button) ImageView recImageView;
  @BindView(R.id.rec_textview) TextView recTextView;
  @BindView(R.id.fav_button) ImageView favImageView;

  public ThemeViewHolder(View itemView) {
    super(itemView);
  }
}