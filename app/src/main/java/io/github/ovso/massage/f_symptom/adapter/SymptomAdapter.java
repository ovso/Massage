package io.github.ovso.massage.f_symptom.adapter;

import android.view.View;
import io.github.ovso.massage.R;
import io.github.ovso.massage.f_symptom.model.Symptom;
import io.github.ovso.massage.framework.ObjectUtils;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.massage.framework.listener.OnCustomRecyclerItemClickListener;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class SymptomAdapter extends BaseRecyclerAdapter
    implements SymptomAdapterView, BaseAdapterDataModel<Symptom> {
  public final static int TYPE_SITE = 0;
  public final static int TYPE_VIDEO = 1;
  private List<Symptom> items = new ArrayList<>();
  @Accessors(chain = true) @Setter private OnCustomRecyclerItemClickListener<Symptom>
      onRecyclerItemClickListener;

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new SymptomViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_symptom_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
    if (viewHolder instanceof SymptomViewHolder) {
      Symptom item = items.get(position);
      SymptomViewHolder holder = (SymptomViewHolder) viewHolder;
      //holder.setIsRecyclable(false);
      holder.titleTextview.setText(item.getTitle());
      int iconImage = R.drawable.ic_ondemand_video;

      switch (item.getType()) {
        case TYPE_SITE:
          iconImage = R.drawable.ic_web;
          break;
        case TYPE_VIDEO:
          iconImage = R.drawable.ic_ondemand_video;
          break;
      }
      holder.videoImageView.setImageResource(iconImage);
      holder.recTextView.setText(String.valueOf(item.getRec()));
      holder.itemView.setOnClickListener(view -> {
        if (!ObjectUtils.isEmpty(onRecyclerItemClickListener)) {
          onRecyclerItemClickListener.onItemClick(position, item);
        }
      });
      holder.recImageView.setOnClickListener(view -> {
        if(!ObjectUtils.isEmpty(onRecyclerItemClickListener)) {
          onRecyclerItemClickListener.onRecommendClick(position, item);
        }
      });
      holder.favImageView.setOnClickListener(view -> {
        if(!ObjectUtils.isEmpty(onRecyclerItemClickListener)) {
          onRecyclerItemClickListener.onFavoriteClick(position, item);
        }
      });
    }
  }

  @Override public int getItemCount() {
    return items.size();
  }

  @Override public void refresh() {
    notifyItemRangeChanged(0, getSize());
  }

  @Override public void add(Symptom changeItem) {
    items.set(0, changeItem);
  }

  @Override public void addAll(List<Symptom> items) {
    this.items.addAll(items);
  }

  @Override public Symptom remove(int position) {
    return null;
  }

  @Override public Symptom getItem(int position) {
    return null;
  }

  @Override public void add(int index, Symptom item) {

  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
  }

  @Override public void refresh(int position) {
    notifyItemChanged(position);
  }
}
