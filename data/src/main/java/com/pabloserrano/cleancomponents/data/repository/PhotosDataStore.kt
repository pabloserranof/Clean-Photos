package com.pabloserrano.cleancomponents.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.pabloserrano.cleancomponents.data.model.PhotoEntity

/**
 * Interface defining methods for the data operations related to Photos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface PhotosDataStore {

    fun clearPhotos(): Completable

    fun savePhotos(photos: List<PhotoEntity>): Completable

    fun getCuratedPhotos(): Flowable<List<PhotoEntity>>

    fun isCached(): Single<Boolean>

}