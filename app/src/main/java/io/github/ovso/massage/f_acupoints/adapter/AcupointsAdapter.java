package io.github.ovso.massage.f_acupoints.adapter;

import android.view.View;
import com.jakewharton.rxbinding2.view.RxView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.f_acupoints.model.Acupoints;
import io.github.ovso.massage.framework.ConversionUtility;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.massage.framework.listener.OnCustomRecyclerItemClickListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class AcupointsAdapter extends BaseRecyclerAdapter implements AcupointsAdapterView,
    io.github.ovso.massage.framework.adapter.BaseAdapterDataModel<SelectableItem<Acupoints>> {
  public final static int TYPE_SITE = 0;
  public final static int TYPE_VIDEO = 1;

  private List<SelectableItem<Acupoints>> selectableItems = new ArrayList<>();

  @Accessors(chain = true) @Setter
  private OnCustomRecyclerItemClickListener<SelectableItem<Acupoints>> onRecyclerItemClickListener;

  @Accessors(chain = true) private @Setter CompositeDisposable compositeDisposable;

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new AcupointsViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_acupoints_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
    if (viewHolder instanceof AcupointsViewHolder) {
      SelectableItem<Acupoints> selectableItem = this.selectableItems.get(position);
      Acupoints item = selectableItem.getItem();
      AcupointsViewHolder holder = (AcupointsViewHolder) viewHolder;

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
      holder.recTextView.setText(ConversionUtility.convertUnit(item.getRec()));
      if (selectableItem.isFavorite()) {
        holder.favImageView.setImageResource(R.drawable.ic_favorite);
      } else {
        holder.favImageView.setImageResource(R.drawable.ic_favorite_border);
      }

      compositeDisposable.add(RxView.clicks(holder.itemView)
          .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(o -> onRecyclerItemClickListener.onItemClick(selectableItem)));
      compositeDisposable.add(RxView.clicks(holder.recImageView)
          .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(o -> onRecyclerItemClickListener.onRecommendClick(position, selectableItem)));
      compositeDisposable.add(RxView.clicks(holder.favImageView)
          .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(o -> onRecyclerItemClickListener.onFavoriteClick(position, selectableItem)));
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void add(SelectableItem<Acupoints> changeItem) {
    selectableItems.add(changeItem);
  }

  @Override public void addAll(List<SelectableItem<Acupoints>> items) {
    this.selectableItems.addAll(items);
  }

  @Override public SelectableItem<Acupoints> remove(int position) {
    return this.selectableItems.remove(position);
  }

  @Override public SelectableItem<Acupoints> getItem(int position) {
    return null;
  }

  @Override public void add(int index, SelectableItem<Acupoints> item) {
    this.selectableItems.add(index, item);
  }

  @Override public int getSize() {
    return selectableItems.size();
  }

  @Override public void clear() {
    selectableItems.clear();
  }

  @Override public void refresh() {
    notifyItemRangeChanged(0, getSize());
  }

  @Override public void refresh(int position) {
    notifyItemChanged(position);
  }

  @Override public void refreshRemove() {
    notifyItemRangeRemoved(0, getSize());
  }

  @Override public void refreshRemove(int position) {
    notifyItemRemoved(position);
  }
}
