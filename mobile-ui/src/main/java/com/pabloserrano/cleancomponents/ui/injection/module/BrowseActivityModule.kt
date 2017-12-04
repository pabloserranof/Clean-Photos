package com.pabloserrano.cleancomponents.ui.injection.module

import dagger.Module
import dagger.Provides
import com.pabloserrano.cleancomponents.domain.interactor.browse.GetCuratedPhotos
import com.pabloserrano.cleancomponents.presentation.browse.BrowsePhotosViewModelFactory
import com.pabloserrano.cleancomponents.presentation.mapper.PhotosMapper


/**
 * Module used to provide dependencies at an activity-level.
 */
@Module
open class BrowseActivityModule {

    @Provides
    fun provideBrowsePhotosViewModelFactory(getCuratedPhotos: GetCuratedPhotos,
                                            photosMapper: PhotosMapper):
            BrowsePhotosViewModelFactory {
        return BrowsePhotosViewModelFactory(getCuratedPhotos, photosMapper)
    }

}
