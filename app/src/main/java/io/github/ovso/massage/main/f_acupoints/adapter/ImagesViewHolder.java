package io.github.ovso.massage.main.f_acupoints.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;

public class ImagesViewHolder extends BaseRecyclerAdapter.BaseViewHolder {

    @BindView(R.id.doc_url_textview)
    TextView docUrlTextview;
    @BindView(R.id.imageview)
    ImageView imageview;
    public ImagesViewHolder(View itemView) {
        super(itemView);
    }
}