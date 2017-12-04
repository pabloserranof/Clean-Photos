package com.pabloserrano.cleancomponents.ui.injection.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import com.pabloserrano.cleancomponents.domain.executor.PostExecutionThread
import com.pabloserrano.cleancomponents.domain.repository.PhotoRepository
import com.pabloserrano.cleancomponents.ui.injection.ApplicationComponent
import com.pabloserrano.cleancomponents.ui.injection.module.ActivityBindingModule
import com.pabloserrano.cleancomponents.ui.injection.module.TestApplicationModule
import com.pabloserrano.cleancomponents.ui.injection.scopes.PerApplication
import com.pabloserrano.cleancomponents.ui.test.TestApplication

@Component(modules = arrayOf(TestApplicationModule::class, ActivityBindingModule::class,
        AndroidSupportInjectionModule::class))
@PerApplication
interface TestApplicationComponent : ApplicationComponent {

    fun photoRepository(): PhotoRepository

    fun postExecutionThread(): PostExecutionThread

    fun inject(application: TestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestApplicationComponent
    }

}