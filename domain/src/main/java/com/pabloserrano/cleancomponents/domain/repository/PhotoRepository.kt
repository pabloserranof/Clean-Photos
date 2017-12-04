package com.pabloserrano.cleancomponents.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.domain.model.PhotoDomain

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface PhotoRepository {

    fun clearPhotos(): Completable

    fun savePhotos(photos: List<PhotoDomain>): Completable

    fun getCuratedPhotos(): Flowable<List<PhotoDomain>>

}