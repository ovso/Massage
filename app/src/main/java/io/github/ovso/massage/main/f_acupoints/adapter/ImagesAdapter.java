package io.github.ovso.massage.main.f_acupoints.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.framework.adapter.BaseAdapterView;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.massage.main.f_acupoints.model.Documents;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class ImagesAdapter extends BaseRecyclerAdapter
        implements BaseAdapterView, BaseAdapterDataModel<Documents> {

    public ImagesAdapter(CompositeDisposable compositeDisposable, OnAcuRecyclerItemClickListener<Documents> l) {
        this.compositeDisposable = compositeDisposable;
        onRecyclerItemClickListener = l;
    }

    private final List<Documents> items = new ArrayList<>();

    private final CompositeDisposable compositeDisposable;
    private final OnAcuRecyclerItemClickListener<Documents> onRecyclerItemClickListener;

    @Override
    protected BaseViewHolder createViewHolder(View view, int viewType) {
        return new ImagesViewHolder(view);
    }

    @Override
    public int getLayoutRes(int viewType) {
        return R.layout.images_entry;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        final Context context = viewHolder.itemView.getContext();
        if (viewHolder instanceof ImagesViewHolder) {
            ImagesViewHolder holder = (ImagesViewHolder) viewHolder;
            final Documents documents = items.get(position);

            //holder.setIsRecyclable(false);
            Glide.with(context)
                    .load(documents.thumbnail_url)
                    .override(Target.SIZE_ORIGINAL)
                    .into(holder.imageview);
            holder.docUrlTextview.setText(documents.doc_url);
            compositeDisposable.add(RxView.clicks(holder.docUrlTextview)
                    .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> onRecyclerItemClickListener.onDocUrlItemClick(documents)));
            compositeDisposable.add(RxView.clicks(holder.itemView)
                    .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> onRecyclerItemClickListener.onItemClick(documents)));
        }
    }

    @Override
    public int getItemCount() {
        return getSize();
    }

    @Override
    public void add(Documents item) {
        items.add(item);
    }

    @Override
    public void addAll(List<Documents> items) {
        this.items.addAll(items);
    }

    @Override
    public Documents remove(int position) {
        return this.items.remove(position);
    }

    @Override
    public Documents getItem(int position) {
        return items.get(position);
    }

    @Override
    public void add(int index, Documents item) {
        this.items.add(index, item);
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public void refresh() {
        notifyItemRangeChanged(0, getSize());
    }
}
