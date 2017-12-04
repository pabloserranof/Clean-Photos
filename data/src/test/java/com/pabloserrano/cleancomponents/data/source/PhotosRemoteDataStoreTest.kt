package com.pabloserrano.cleancomponents.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.data.repository.PhotosRemote
import com.pabloserrano.cleancomponents.data.test.factory.PhotoFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PhotosRemoteDataStoreTest {

    private lateinit var photosRemoteDataStore: PhotosRemoteDataStore

    private lateinit var photosRemote: PhotosRemote

    @Before
    fun setUp() {
        photosRemote = mock()
        photosRemoteDataStore = PhotosRemoteDataStore(photosRemote)
    }

    //<editor-fold desc="Clear Photos">
    @Test(expected = UnsupportedOperationException::class)
    fun clearPhotosThrowsException() {
        photosRemoteDataStore.clearPhotos().test()
    }
    //</editor-fold>

    //<editor-fold desc="Save Photos">
    @Test(expected = UnsupportedOperationException::class)
    fun savePhotosThrowsException() {
        photosRemoteDataStore.savePhotos(PhotoFactory.makePhotoEntityList(2)).test()
    }
    //</editor-fold>

    //<editor-fold desc="Get Photos">
    @Test
    fun getPhotosCompletes() {
        stubPhotoCacheGetCuratedPhotos(Flowable.just(PhotoFactory.makePhotoEntityList(2)))
        val testObserver = photosRemote.getCuratedPhotos().test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubPhotoCacheGetCuratedPhotos(single: Flowable<List<PhotoEntity>>) {
        whenever(photosRemote.getCuratedPhotos())
                .thenReturn(single)
    }
    //</editor-fold>

}