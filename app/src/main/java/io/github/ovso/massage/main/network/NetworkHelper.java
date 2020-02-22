package io.github.ovso.massage.main.network;

import android.content.Context;
import io.github.ovso.massage.Security;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class NetworkHelper<T> {

  private String baseUrl;
  protected Context context;

  public NetworkHelper(Context context, String baseUrl) {
    this.context = context;
    this.baseUrl = baseUrl;
  }

  protected T getApi() {
    return createRetrofit().create(getApiClass());
  }

  protected abstract Class<T> getApiClass();

  private Retrofit createRetrofit() {
    return new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(createClient())
        .build();
  }

  private OkHttpClient createClient() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(chain -> {
      Request original = chain.request();
      Request.Builder requestBuilder = original.newBuilder()
          .header("Content-Type", "plain/text")
          .addHeader("Authorization", Security.AUTHORIZATION.getValue());

      Request request = requestBuilder.build();
      return chain.proceed(request);
    });
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    httpClient.addInterceptor(interceptor);
    OkHttpClient client = httpClient.build();
    return client;
  }
}