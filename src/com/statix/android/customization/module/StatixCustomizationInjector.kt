package com.statix.android.customization.module

import android.content.Context
import androidx.activity.ComponentActivity
import com.android.customization.module.ThemePickerInjector
import com.android.customization.module.logging.ThemesUserEventLogger
import com.android.customization.picker.notifications.ui.viewmodel.NotificationSectionViewModel
import com.android.wallpaper.module.CustomizationSections
import com.android.wallpaper.picker.di.modules.BackgroundDispatcher
import com.android.wallpaper.picker.di.modules.MainDispatcher
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

@Singleton
open class StatixCustomizationInjector @Inject constructor(
    @MainDispatcher mainScope: CoroutineScope,
    @MainDispatcher mainDispatcher: CoroutineDispatcher,
    @BackgroundDispatcher bgScope: CoroutineScope,
    @BackgroundDispatcher bgDispatcher: CoroutineDispatcher,
) : ThemePickerInjector(
    mainScope,
    bgDispatcher,
) {

  private var customizationSections: CustomizationSections? = null

  override fun getCustomizationSections(activity: ComponentActivity): CustomizationSections {
    val appContext = activity.applicationContext
    val clockViewFactory = getClockViewFactory(activity)
    val resources = activity.resources
    return customizationSections
        ?: StatixCustomizationSections(
                getColorPickerViewModelFactory(
                    context = appContext,
                    wallpaperColorsRepository = getWallpaperColorsRepository(),
                ),
                getKeyguardQuickAffordancePickerViewModelFactory(appContext),
                getNotificationSectionViewModelFactory(appContext),
                getFlags(),
                getClockCarouselViewModelFactory(
                    interactor = getClockPickerInteractor(appContext),
		    clockViewFactory = clockViewFactory,
		    resources = resources,
                ),
                clockViewFactory,
                getThemedIconSnapshotRestorer(appContext),
                getThemedIconInteractor(),
                getColorPickerInteractor(appContext, getWallpaperColorsRepository()),
		getUserEventLogger(),
            )
            .also { customizationSections = it }
  }
}
