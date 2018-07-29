package io.github.ovso.massage.framework.listener;

public interface OnCustomRecyclerItemClickListener<T>
    extends OnRecyclerItemClickListener<T> {
  void onFavoriteClick(int position, T item);

  void onVideoClick(int position, T item);

  void onRecommendClick(int position, T item);
}
