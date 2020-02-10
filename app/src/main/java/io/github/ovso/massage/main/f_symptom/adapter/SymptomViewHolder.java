package io.github.ovso.massage.main.f_symptom.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;

class SymptomViewHolder extends BaseRecyclerAdapter.BaseViewHolder {

    @BindView(R.id.title_textview)
    TextView titleTextview;
    @BindView(R.id.video_button)
    ImageButton videoButton;

    SymptomViewHolder(View itemView) {
        super(itemView);
    }
}