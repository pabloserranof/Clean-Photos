package com.pabloserrano.cleancomponents.ui.injection.component

import dagger.Subcomponent
import dagger.android.AndroidInjector
import com.pabloserrano.cleancomponents.ui.browse.BrowseActivity

@Subcomponent
interface BrowseActivitySubComponent : AndroidInjector<BrowseActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<BrowseActivity>()

}