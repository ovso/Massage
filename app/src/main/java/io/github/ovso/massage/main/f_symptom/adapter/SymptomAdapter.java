package io.github.ovso.massage.main.f_symptom.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import com.jakewharton.rxbinding2.view.RxView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.main.f_symptom.model.Symptom;
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

public class SymptomAdapter extends BaseRecyclerAdapter implements SymptomAdapterView,
    io.github.ovso.massage.framework.adapter.BaseAdapterDataModel<SelectableItem<Symptom>> {

  private List<SelectableItem<Symptom>> selectableItems = new ArrayList<>();

  @Accessors(chain = true) @Setter
  private OnCustomRecyclerItemClickListener<SelectableItem<Symptom>> onRecyclerItemClickListener;

  @Accessors(chain = true) private @Setter CompositeDisposable compositeDisposable;

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
      Context context = holder.itemView.getContext();
      if (TextUtils.isEmpty(item.getUrl())) {
        holder.titleTextview.setTextColor(ContextCompat.getColor(context, R.color.color_500));
        holder.titleTextview.setTypeface(Typeface.DEFAULT);
      } else {
        holder.titleTextview.setTextColor(ContextCompat.getColor(context, android.R.color.black));
      }
      int iconImage;
      if (!TextUtils.isEmpty(item.getVideo_id())) {
        iconImage = R.drawable.ic_ondemand_video_on;
      } else {
        iconImage = R.drawable.ic_ondemand_video;
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
      compositeDisposable.add(RxView.clicks(holder.recTextView)
          .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(o -> onRecyclerItemClickListener.onRecommendClick(position, selectableItem)));
      compositeDisposable.add(RxView.clicks(holder.favImageView)
          .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(o -> onRecyclerItemClickListener.onFavoriteClick(position, selectableItem)));
      compositeDisposable.add(RxView.clicks(holder.videoImageView)
          .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(o -> onRecyclerItemClickListener.onVideoClick(position, selectableItem)));
      compositeDisposable.add(RxView.longClicks(holder.videoImageView)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(o -> onRecyclerItemClickListener.onItemLongClick(selectableItem)));
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void add(SelectableItem<Symptom> changeItem) {
    selectableItems.add(changeItem);
  }

  @Override public void addAll(List<SelectableItem<Symptom>> items) {
    this.selectableItems.addAll(items);
  }

  @Override public SelectableItem<Symptom> remove(int position) {
    return this.selectableItems.remove(position);
  }

  @Override public SelectableItem<Symptom> getItem(int position) {
    return null;
  }

  @Override public void add(int index, SelectableItem<Symptom> item) {
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
