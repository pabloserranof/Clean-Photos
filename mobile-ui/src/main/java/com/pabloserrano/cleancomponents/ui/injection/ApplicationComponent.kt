package com.pabloserrano.cleancomponents.ui.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import com.pabloserrano.cleancomponents.ui.MyApplication
import com.pabloserrano.cleancomponents.ui.injection.module.ActivityBindingModule
import com.pabloserrano.cleancomponents.ui.injection.module.ApplicationModule
import com.pabloserrano.cleancomponents.ui.injection.scopes.PerApplication

@PerApplication
@Component(modules = arrayOf(ActivityBindingModule::class, ApplicationModule::class,
        AndroidSupportInjectionModule::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(app: MyApplication)

}
