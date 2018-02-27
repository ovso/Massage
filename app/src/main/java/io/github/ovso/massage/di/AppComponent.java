package io.github.ovso.massage.di;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;
import io.github.ovso.massage.app.MyApplication;

/**
 * Created by jaeho on 2017. 10. 16
 */
@Component(modules = {
    AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilder.class
}) public interface AppComponent extends AndroidInjector<DaggerApplication> {
  @Component.Builder interface Builder {
    @BindsInstance Builder application(Application application);

    AppComponent build();
  }

  void inject(MyApplication app);
}