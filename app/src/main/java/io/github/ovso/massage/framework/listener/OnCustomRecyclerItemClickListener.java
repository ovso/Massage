package io.github.ovso.massage.framework.listener;

/**
 * Created by jaeho on 2017. 11. 28
 */

public interface OnCustomRecyclerItemClickListener<T>
    extends OnRecyclerItemClickListener<T>, OnRecyclerItemLongClickListener<T> {
  void onFavoriteClick(int position, T item);

  void onVideoClick(int position, T item);

  void onRecommendClick(int position, T item);
}
