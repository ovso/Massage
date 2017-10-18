package io.github.ovso.massage.framework.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;

/**
 * Created by jaeho on 2017. 10. 18
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
  public BaseViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}