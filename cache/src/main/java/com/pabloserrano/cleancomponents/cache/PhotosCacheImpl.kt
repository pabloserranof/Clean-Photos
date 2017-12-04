package com.pabloserrano.cleancomponents.cache

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.pabloserrano.cleancomponents.cache.db.PhotosDatabase
import com.pabloserrano.cleancomponents.cache.mapper.PhotoEntityMapper
import com.pabloserrano.cleancomponents.cache.model.CachedPhoto
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import com.pabloserrano.cleancomponents.data.repository.PhotosCache
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving Photo instances. This class implements the
 * [PhotosCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PhotosCacheImpl @Inject constructor(val photosDatabase: PhotosDatabase,
                                          private val entityMapper: PhotoEntityMapper,
                                          private val preferencesHelper: PreferencesHelper) :
        PhotosCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    /**
     * Retrieve an instance from the database, used for tests.
     */
    internal fun getDatabase(): PhotosDatabase {
        return photosDatabase
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    override fun clearPhotos(): Completable {
        return Completable.defer {
            photosDatabase.cachedPhotoDao().clearPhotos()
            Completable.complete()
        }
    }

    /**
     * Save the given list of [PhotoEntity] instances to the database.
     */
    override fun savePhotos(photos: List<PhotoEntity>): Completable {
        return Completable.defer {
            photos.forEach {
                photosDatabase.cachedPhotoDao().insertPhoto(
                        entityMapper.mapToCached(it))
            }
            Completable.complete()
        }
    }

    /**
     * Retrieve a list of [PhotoEntity] instances from the database.
     */
    override fun getCuratedPhotos(): Flowable<List<PhotoEntity>> {
        return Flowable.defer {
            Flowable.just(photosDatabase.cachedPhotoDao().getCuratedPhotos())
        }.map {
            it.map { entityMapper.mapFromCached(it) }
        }
    }

    /**
     * Check whether there are instances of [CachedPhoto] stored in the cache.
     */
    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(photosDatabase.cachedPhotoDao().getCuratedPhotos().isNotEmpty())
        }
    }

    /**
     * Set a point in time at when the cache was last updated.
     */
    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time.
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}