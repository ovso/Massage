package io.github.ovso.massage.main.f_acupoints.adapter;

import io.github.ovso.massage.framework.listener.OnRecyclerItemClickListener;

/**
 * Created by jaeho on 2018. 1. 28..
 */

public interface OnAcuRecyclerItemClickListener<T> extends OnRecyclerItemClickListener<T> {
  void onDocUrlItemClick(T item);
}
