package io.github.ovso.massage.main.f_acupoints.network;

import io.github.ovso.massage.main.f_acupoints.model.DResult;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImagesApi {

    @GET("/v2/search/image")
    Single<DResult> getImages(
            @Query("query") String query,
            @Query("sort") String sort,
            @Query("page") int page,
            @Query("size") int size);
}
