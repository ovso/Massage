package io.github.ovso.massage.main.f_theme.adapter;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;

class ThemeViewHolder extends BaseRecyclerAdapter.BaseViewHolder {

    @BindView(R.id.title_textview)
    TextView titleTextview;

    ThemeViewHolder(View itemView) {
        super(itemView);
    }
}