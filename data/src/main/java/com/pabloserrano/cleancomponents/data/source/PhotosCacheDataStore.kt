package com.pabloserrano.cleancomponents.data.source

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import com.pabloserrano.cleancomponents.data.repository.PhotosCache
import com.pabloserrano.cleancomponents.data.repository.PhotosDataStore
import javax.inject.Inject

/**
 * Implementation of the [PhotosDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class PhotosCacheDataStore @Inject constructor(private val photosCache: PhotosCache) :
        PhotosDataStore {
    override fun clearPhotos(): Completable {
        return photosCache.clearPhotos()
    }

    override fun savePhotos(photos: List<PhotoEntity>): Completable {
        return photosCache.savePhotos(photos)
                .doOnComplete{
                    photosCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    override fun getCuratedPhotos(): Flowable<List<PhotoEntity>> {
        return photosCache.getCuratedPhotos()
    }

    /**
     * Retrieve a list of [PhotoEntity] instance from the cache
     */
    override fun isCached(): Single<Boolean> {
        return photosCache.isCached()
    }

}