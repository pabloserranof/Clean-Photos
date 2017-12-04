package com.pabloserrano.cleancomponents.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import com.pabloserrano.cleancomponents.data.repository.PhotosCache
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.pabloserrano.cleancomponents.data.source.PhotoDataStoreFactory
import com.pabloserrano.cleancomponents.data.source.PhotosCacheDataStore
import com.pabloserrano.cleancomponents.data.source.PhotosRemoteDataStore

@RunWith(JUnit4::class)
class PhotosDataStoreFactoryTest {

    private lateinit var photoDataStoreFactory: PhotoDataStoreFactory

    private lateinit var photosCache: PhotosCache
    private lateinit var photoCacheDataStore: PhotosCacheDataStore
    private lateinit var photoRemoteDataStore: PhotosRemoteDataStore

    @Before
    fun setUp() {
        photosCache = mock()
        photoCacheDataStore = mock()
        photoRemoteDataStore = mock()
        photoDataStoreFactory = PhotoDataStoreFactory(photosCache,
                photoCacheDataStore, photoRemoteDataStore)
    }

    //<editor-fold desc="Retrieve Data Store">
    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubPhotoCacheIsCached(Single.just(false))
        val photoDataStore = photoDataStoreFactory.retrieveDataStore(false)
        assert(photoDataStore is PhotosRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubPhotoCacheIsCached(Single.just(true))
        stubPhotoCacheIsExpired(true)
        val photoDataStore = photoDataStoreFactory.retrieveDataStore(true)
        assert(photoDataStore is PhotosRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubPhotoCacheIsCached(Single.just(true))
        stubPhotoCacheIsExpired(false)
        val photoDataStore = photoDataStoreFactory.retrieveDataStore(true)
        assert(photoDataStore is PhotosCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Remote Data Store">
    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val photoDataStore = photoDataStoreFactory.retrieveRemoteDataStore()
        assert(photoDataStore is PhotosRemoteDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Cache Data Store">
    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val photoDataStore = photoDataStoreFactory.retrieveCacheDataStore()
        assert(photoDataStore is PhotosCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubPhotoCacheIsCached(single: Single<Boolean>) {
        whenever(photosCache.isCached())
                .thenReturn(single)
    }

    private fun stubPhotoCacheIsExpired(isExpired: Boolean) {
        whenever(photosCache.isExpired())
                .thenReturn(isExpired)
    }
    //</editor-fold>

}