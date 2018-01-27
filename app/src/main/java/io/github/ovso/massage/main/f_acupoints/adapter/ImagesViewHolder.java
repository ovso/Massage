package io.github.ovso.massage.main.f_acupoints.adapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class ImagesViewHolder extends BaseRecyclerAdapter.BaseViewHolder {

  @BindView(R.id.price) TextView price;
  @BindView(R.id.imageview) ImageView imageview;
  @BindView(R.id.favorite_button) ImageButton favoriteButton;
  @BindView(R.id.product_entry) CardView productEntry;

  public ImagesViewHolder(View itemView) {
    super(itemView);
  }
}