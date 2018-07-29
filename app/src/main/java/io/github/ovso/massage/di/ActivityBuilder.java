package io.github.ovso.massage.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import io.github.ovso.massage.main.MainActivity;
import io.github.ovso.massage.main.di.MainActivityModule;

@Module(includes = { AndroidSupportInjectionModule.class}) public abstract class ActivityBuilder {
  @ContributesAndroidInjector(modules = { MainActivityModule.class })
  abstract MainActivity bindMainActivity();
}
