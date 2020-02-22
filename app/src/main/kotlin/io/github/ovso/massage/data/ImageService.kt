package io.github.ovso.massage.data

import io.github.ovso.massage.main.f_acupoints.model.DResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ImageService {
    @GET("/v2/search/image")
    open fun getImages(
        @Header("Authorization") key: String,
        @QueryMap map: HashMap<String, Any?>,
        @Query("query") query: String?,
        @Query("sort") sort: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<DResult?>?
}