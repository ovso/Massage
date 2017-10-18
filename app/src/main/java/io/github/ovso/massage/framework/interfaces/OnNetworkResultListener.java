package io.github.ovso.massage.framework.interfaces;

/**
 * Created by jaeho on 2017. 10. 18..
 */

public interface OnNetworkResultListener<T> {
  void onPre();
  void onResult(T result);
  void onPost();
  void onError();
}