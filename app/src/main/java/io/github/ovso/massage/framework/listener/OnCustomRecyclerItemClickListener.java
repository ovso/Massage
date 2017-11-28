package io.github.ovso.massage.framework.listener;

/**
 * Created by jaeho on 2017. 11. 28
 */

public interface OnCustomRecyclerItemClickListener<T> extends OnRecyclerItemClickListener<T> {
  void onItemClick(int position, T item);
  void onRecommendClick(int position, T item);
}
