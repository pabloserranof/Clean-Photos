package com.pabloserrano.cleancomponents.data.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.data.repository.PhotosCache
import com.pabloserrano.cleancomponents.data.test.factory.PhotoFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PhotosCacheDataStoreTest {

    private lateinit var photoCacheDataStore: PhotosCacheDataStore

    private lateinit var photosCache: PhotosCache

    @Before
    fun setUp() {
        photosCache = mock()
        photoCacheDataStore = PhotosCacheDataStore(photosCache)
    }

    //<editor-fold desc="Clear Photos">
    @Test
    fun clearPhotosCompletes() {
        stubPhotoCacheClearPhotos(Completable.complete())
        val testObserver = photoCacheDataStore.clearPhotos().test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Save Photos">
    @Test
    fun savePhotosCompletes() {
        stubPhotoCacheSavePhotos(Completable.complete())
        val testObserver = photoCacheDataStore.savePhotos(
                PhotoFactory.makePhotoEntityList(2)).test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Get Photos">
    @Test
    fun getPhotosCompletes() {
        stubPhotoCacheGetPhotos(Flowable.just(PhotoFactory.makePhotoEntityList(2)))
        val testObserver = photoCacheDataStore.getCuratedPhotos().test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubPhotoCacheSavePhotos(completable: Completable) {
        whenever(photosCache.savePhotos(any()))
                .thenReturn(completable)
    }

    private fun stubPhotoCacheGetPhotos(single: Flowable<List<PhotoEntity>>) {
        whenever(photosCache.getCuratedPhotos())
                .thenReturn(single)
    }

    private fun stubPhotoCacheClearPhotos(completable: Completable) {
        whenever(photosCache.clearPhotos())
                .thenReturn(completable)
    }
    //</editor-fold>

}