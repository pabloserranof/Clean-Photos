package com.pabloserrano.cleancomponents.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.pabloserrano.cleancomponents.data.model.PhotoEntity

/**
 * Interface defining methods for the caching of Photos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface PhotosCache {

    /**
     * Clear all Photos from the cache.
     */
    fun clearPhotos(): Completable

    /**
     * Save a given list of Photos to the cache.
     */
    fun savePhotos(photos: List<PhotoEntity>): Completable

    /**
     * Retrieve a list of Photos, from the cache.
     */
    fun getCuratedPhotos(): Flowable<List<PhotoEntity>>

    /**
     * Check whether there is a list of Photos stored in the cache.
     *
     * @return true if the list is cached, otherwise false
     */
    fun isCached(): Single<Boolean>

    /**
     * Set a point in time at when the cache was last updated.
     *
     * @param lastCache the point in time at when the cache was last updated
     */
    fun setLastCacheTime(lastCache: Long)

    /**
     * Check if the cache is expired.
     *
     * @return true if the cache is expired, otherwise false
     */
    fun isExpired(): Boolean

}