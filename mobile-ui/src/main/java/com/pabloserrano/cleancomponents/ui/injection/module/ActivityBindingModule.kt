package com.pabloserrano.cleancomponents.ui.injection.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.pabloserrano.cleancomponents.ui.browse.BrowseActivity
import com.pabloserrano.cleancomponents.ui.injection.scopes.PerActivity

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(BrowseActivityModule::class))
    abstract fun bindMainActivity(): BrowseActivity

}