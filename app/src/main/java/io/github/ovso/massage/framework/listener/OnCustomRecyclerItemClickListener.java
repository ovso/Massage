package io.github.ovso.massage.framework.listener;

public interface OnCustomRecyclerItemClickListener<T>
    extends OnRecyclerItemClickListener<T> {
  void onVideoClick(int position, T item);
}