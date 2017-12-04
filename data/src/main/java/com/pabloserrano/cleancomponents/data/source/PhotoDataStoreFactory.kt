package com.pabloserrano.cleancomponents.data.source

import com.pabloserrano.cleancomponents.data.repository.PhotosCache
import com.pabloserrano.cleancomponents.data.repository.PhotosDataStore
import javax.inject.Inject

/**
 * Create an instance of a PhotosDataStore
 */
open class PhotoDataStoreFactory @Inject constructor(
        private val photosCache: PhotosCache,
        private val photoCacheDataStore: PhotosCacheDataStore,
        private val photoRemoteDataStore: PhotosRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): PhotosDataStore {
        if (isCached && !photosCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): PhotosDataStore {
        return photoCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): PhotosDataStore {
        return photoRemoteDataStore
    }

}