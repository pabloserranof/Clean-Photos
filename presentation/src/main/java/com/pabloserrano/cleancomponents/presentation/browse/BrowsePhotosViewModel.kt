package com.pabloserrano.cleancomponents.presentation.browse

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.subscribers.DisposableSubscriber
import com.pabloserrano.cleancomponents.domain.interactor.browse.GetCuratedPhotos
import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import com.pabloserrano.cleancomponents.presentation.data.Resource
import com.pabloserrano.cleancomponents.presentation.data.ResourceState
import com.pabloserrano.cleancomponents.presentation.mapper.PhotosMapper
import com.pabloserrano.cleancomponents.presentation.model.PhotoView
import javax.inject.Inject

class BrowsePhotosViewModel @Inject internal constructor(
        private val getCuratedPhotos: GetCuratedPhotos,
        private val photosMapper: PhotosMapper) : ViewModel() {

    private val curatedPhotosLiveData: MutableLiveData<Resource<List<PhotoView>>> =
            MutableLiveData()

    init {
        fetchCuratedPhotos()
    }

    override fun onCleared() {
        getCuratedPhotos.dispose()
        super.onCleared()
    }

    fun getCuratedPhotos(): LiveData<Resource<List<PhotoView>>> {
        return curatedPhotosLiveData
    }

    fun fetchCuratedPhotos() {
        curatedPhotosLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getCuratedPhotos.execute(CuratedPhotosSubscriber())
    }

     inner class CuratedPhotosSubscriber: DisposableSubscriber<List<PhotoDomain>>() {

        override fun onComplete() { }

        override fun onNext(t: List<PhotoDomain>) {
            curatedPhotosLiveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map { photosMapper.mapToView(it) }, null))
        }

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
            curatedPhotosLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}