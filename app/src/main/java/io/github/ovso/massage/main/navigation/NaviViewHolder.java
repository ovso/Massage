package io.github.ovso.massage.main.navigation;

import android.view.View;
import android.widget.CheckedTextView;
import butterknife.BindView;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;

/**
 * Created by jaeho on 2017. 10. 25
 */

public class NaviViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
  NaviMenuItem naviMenuItem;
  OnNaviItemSelectedListener onNaviItemSelectedListener;
  @BindView(android.R.id.text1) CheckedTextView checkedTextView;

  public NaviViewHolder(View itemView) {
    super(itemView);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onNaviItemSelectedListener.onNaviItemSelected(naviMenuItem);
      }
    });
  }
}