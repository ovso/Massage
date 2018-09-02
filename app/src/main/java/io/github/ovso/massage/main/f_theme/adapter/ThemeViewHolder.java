package io.github.ovso.massage.main.f_theme.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;

public class ThemeViewHolder extends BaseRecyclerAdapter.BaseViewHolder {

  @BindView(R.id.title_textview) TextView titleTextview;
  @BindView(R.id.video_button) ImageButton videoButton;
  @BindView(R.id.fav_button) ImageButton favButton;

  public ThemeViewHolder(View itemView) {
    super(itemView);
  }
}