package com.pabloserrano.cleancomponents.presentation.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.pabloserrano.cleancomponents.domain.interactor.browse.GetCuratedPhotos
import com.pabloserrano.cleancomponents.presentation.mapper.PhotosMapper

open class BrowsePhotosViewModelFactory(
        private val getCuratedPhotos: GetCuratedPhotos,
        private val photosMapper: PhotosMapper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowsePhotosViewModel::class.java)) {
            return BrowsePhotosViewModel(getCuratedPhotos, photosMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}