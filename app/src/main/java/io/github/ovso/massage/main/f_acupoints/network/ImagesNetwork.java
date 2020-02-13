package io.github.ovso.massage.main.f_acupoints.network;

import android.content.Context;
import io.github.ovso.massage.main.network.NetworkHelper;
import io.github.ovso.massage.main.f_acupoints.model.DResult;
import io.reactivex.Single;

/**
 * Created by jaeho on 2018. 1. 25
 */

public class ImagesNetwork extends NetworkHelper<ImagesApi> {
  public ImagesNetwork(Context context, String baseUrl) {
    super(context, baseUrl);
  }

  @Override protected Class<ImagesApi> getApiClass() {
    return ImagesApi.class;
  }

  public Single<DResult> getImages(String query) {
    return getApi().getImages(query, "accuracy", 1, 10);
  }
}