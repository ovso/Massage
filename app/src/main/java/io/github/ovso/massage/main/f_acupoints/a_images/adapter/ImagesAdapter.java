package io.github.ovso.massage.main.f_acupoints.a_images.adapter;

import android.view.View;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.massage.main.f_acupoints.model.Documents;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by jaeho on 2017. 11. 27
 */

public class ImagesAdapter extends BaseRecyclerAdapter
    implements ImagesAdapterView, BaseAdapterDataModel<Documents> {

  private List<Documents> items = new ArrayList<>();

  @Accessors(chain = true) private @Setter CompositeDisposable compositeDisposable;

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new ImagesViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.images_entry;
  }

  @Override public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
    //final Context context = viewHolder.itemView.getContext();
    if (viewHolder instanceof ImagesViewHolder) {
      ImagesViewHolder holder = (ImagesViewHolder) viewHolder;
      Documents documents = items.get(position);

      //holder.setIsRecyclable(false);

      //holder.titleTextview.setText(documents.get);

      /*
      compositeDisposable.add(RxView.clicks(holder.itemView)
          .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(o -> onRecyclerItemClickListener.onItemClick(selectableItem)));
      */
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void add(Documents item) {
    items.add(item);
  }

  @Override public void addAll(List<Documents> items) {
    this.items.addAll(items);
  }

  @Override public Documents remove(int position) {
    return this.items.remove(position);
  }

  @Override public Documents getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, Documents item) {
    this.items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
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
