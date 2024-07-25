package com.statix.android.customization.picker

import android.app.Application
import com.android.wallpaper.module.InjectorProvider
import com.statix.android.customization.module.StatixCustomizationInjector
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp(Application::class)
class StatixCustomizationPickerApplication : Hilt_StatixCustomizationPickerApplication() {

  @Inject
  lateinit var injector: StatixCustomizationInjector

  override fun onCreate() {
    super.onCreate()

    InjectorProvider.setInjector(injector)
  }
}
