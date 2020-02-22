package io.github.ovso.massage.framework.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public abstract class BaseRecyclerAdapter
    extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {

  @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(viewType), parent, false);
    return createViewHolder(view, viewType);
  }

  protected abstract BaseViewHolder createViewHolder(View view, int viewType);

  @LayoutRes
  public abstract int getLayoutRes(int viewType);

  @Override public abstract void onBindViewHolder(BaseViewHolder viewHolder, int position);

  @Override public abstract int getItemCount();

  public static class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}