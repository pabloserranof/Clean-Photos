package com.pabloserrano.cleancomponents.data.repository

import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.data.model.PhotoEntity

/**
 * Interface defining methods for the caching of Photos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface PhotosRemote {

    /**
     * Retrieve a list of Photos, from the cache
     */

    fun getCuratedPhotos(): Flowable<List<PhotoEntity>>

}