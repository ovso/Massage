package io.github.ovso.massage.f_symptom.adapter;

import android.view.View;
import io.github.ovso.massage.R;
import io.github.ovso.massage.f_symptom.model.Symptom;
import io.github.ovso.massage.framework.ObjectUtils;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.massage.framework.listener.OnCustomRecyclerItemClickListener;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class SymptomAdapter extends BaseRecyclerAdapter implements SymptomAdapterView,
    io.github.ovso.massage.framework.adapter.BaseAdapterDataModel<SelectableItem<Symptom>> {
  public final static int TYPE_SITE = 0;
  public final static int TYPE_VIDEO = 1;

  private List<SelectableItem<Symptom>> selectableItems = new ArrayList<>();

  @Accessors(chain = true) @Setter
  private OnCustomRecyclerItemClickListener<SelectableItem<Symptom>> onRecyclerItemClickListener;

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new SymptomViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_symptom_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
    if (viewHolder instanceof SymptomViewHolder) {
      SelectableItem<Symptom> selectableItem = this.selectableItems.get(position);
      Symptom item = selectableItem.getItem();
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
      if (selectableItem.isFavorite()) {
        holder.favImageView.setImageResource(R.drawable.ic_favorite);
      } else {
        holder.favImageView.setImageResource(R.drawable.ic_favorite_border);
      }
      holder.itemView.setOnClickListener(view -> {
        if (!ObjectUtils.isEmpty(onRecyclerItemClickListener)) {
          onRecyclerItemClickListener.onItemClick(position, selectableItem);
        }
      });
      holder.recImageView.setOnClickListener(view -> {
        if (!ObjectUtils.isEmpty(onRecyclerItemClickListener)) {
          onRecyclerItemClickListener.onRecommendClick(position, selectableItem);
        }
      });
      holder.favImageView.setOnClickListener(view -> {
        if (!ObjectUtils.isEmpty(onRecyclerItemClickListener)) {
          onRecyclerItemClickListener.onFavoriteClick(position, selectableItem);
        }
      });
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyItemRangeChanged(0, getSize());
  }

  @Override public void add(SelectableItem<Symptom> changeItem) {

  }

  @Override public void addAll(List<SelectableItem<Symptom>> items) {
    this.selectableItems.addAll(items);
  }

  @Override public SelectableItem<Symptom> remove(int position) {
    return null;
  }

  @Override public SelectableItem<Symptom> getItem(int position) {
    return null;
  }

  @Override public void add(int index, SelectableItem<Symptom> item) {

  }

  @Override public int getSize() {
    return selectableItems.size();
  }

  @Override public void clear() {
    selectableItems.clear();
  }

  @Override public void refresh(int position) {
    notifyItemChanged(position);
  }

  @Override public void removeRefresh() {
    notifyItemRangeRemoved(0, getSize());
  }
}
