package io.github.ovso.massage.di;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by jaeho on 2017. 10. 16
 */
@Module public class AppModule {
  @Provides @Singleton Context provideContext(Application application) {
    return application;
  }
}
