package io.github.ovso.massage.di;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import io.github.ovso.massage.app.MyApplication;

/**
 * Created by jaeho on 2017. 10. 16
 */
@Component(modules = {
    AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class
}) public interface AppComponent {
  @Component.Builder interface Builder {
    @BindsInstance Builder application(Application application);

    AppComponent build();
  }

  void inject(MyApplication app);
}