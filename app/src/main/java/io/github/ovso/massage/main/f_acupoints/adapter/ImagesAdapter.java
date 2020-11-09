package io.github.ovso.massage.main.f_acupoints.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

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

    private final static int VIEW_TYPE_NORMAL = 100;
    private final static int VIEW_TYPE_ADS = 101;
    public ImagesAdapter(CompositeDisposable compositeDisposable, OnAcuRecyclerItemClickListener<Documents> l) {
        this.compositeDisposable = compositeDisposable;
        onRecyclerItemClickListener = l;
    }

    private final List<Documents> items = new ArrayList<>();

    private final CompositeDisposable compositeDisposable;
    private final OnAcuRecyclerItemClickListener<Documents> onRecyclerItemClickListener;

    @Override
    protected BaseViewHolder createViewHolder(View parent, int viewType) {
        if(VIEW_TYPE_NORMAL == viewType) {
            return new ImagesViewHolder(parent);
        } else {
            return NativeAdsViewHolder.create((ViewGroup)parent);
        }
    }

    @Override
    public int getLayoutRes(int viewType) {
        if(VIEW_TYPE_NORMAL == viewType) {
            return R.layout.item_acupoints;
        } else {
            return R.layout.item_native_ads_small;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ((getItem(position) instanceof NativeAdsModel)) {
            return VIEW_TYPE_ADS;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        final Context context = viewHolder.itemView.getContext();
        if (viewHolder instanceof ImagesViewHolder) {
            ImagesViewHolder holder = (ImagesViewHolder) viewHolder;
            final Documents documents = items.get(position);
            Glide.with(context)
                    .load(documents.image_url)
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
        } else if(viewHolder instanceof NativeAdsViewHolder) {
            ((NativeAdsViewHolder)viewHolder).onBindViewHolder();
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
