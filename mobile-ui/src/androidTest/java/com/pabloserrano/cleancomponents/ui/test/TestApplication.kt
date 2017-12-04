package com.pabloserrano.cleancomponents.ui.test

import android.app.Activity
import android.app.Application
import android.support.test.InstrumentationRegistry
import com.pabloserrano.cleancomponents.ui.injection.component.DaggerTestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import com.pabloserrano.cleancomponents.ui.injection.component.TestApplicationComponent
import javax.inject.Inject

class TestApplication: Application(), HasActivityInjector {

    @Inject lateinit var injector: DispatchingAndroidInjector<Activity>

    private lateinit var appComponent: TestApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestApplicationComponent.builder().application(this).build()
        appComponent.inject(this)
    }

    companion object {

        fun appComponent(): TestApplicationComponent {
            return (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication).
                    appComponent
        }

    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }

}