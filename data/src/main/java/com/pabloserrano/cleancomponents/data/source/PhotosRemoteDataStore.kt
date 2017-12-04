package com.pabloserrano.cleancomponents.data.source

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import com.pabloserrano.cleancomponents.data.repository.PhotosDataStore
import com.pabloserrano.cleancomponents.data.repository.PhotosRemote
import javax.inject.Inject

/**
 * Implementation of the [PhotosDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PhotosRemoteDataStore @Inject constructor(private val photosRemote: PhotosRemote) :
        PhotosDataStore {

    override fun savePhotos(photos: List<PhotoEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun clearPhotos(): Completable {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a list of [PhotoEntity] instances from the API
     */

    override fun getCuratedPhotos(): Flowable<List<PhotoEntity>> {
        return photosRemote.getCuratedPhotos()
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

}