package com.pabloserrano.cleancomponents.remote

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import com.pabloserrano.cleancomponents.remote.mapper.PhotoEntityMapper
import com.pabloserrano.cleancomponents.remote.model.PhotoModel
import com.pabloserrano.cleancomponents.remote.test.factory.PhotoFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PhotosRemoteImplTest {

    private lateinit var entityMapper: PhotoEntityMapper
    private lateinit var photoService: PhotoService

    private lateinit var photosRemoteImpl: PhotosRemoteImpl

    @Before
    fun setup() {
        entityMapper = mock()
        photoService = mock()
        photosRemoteImpl = PhotosRemoteImpl(photoService, entityMapper)
    }

    //<editor-fold desc="Get Photos">
    @Test
    fun getPhotosCompletes() {
        stubPhotoServiceGetCuratedPhotos(Flowable.just(PhotoFactory.makePhotoResponse()))
        val testObserver = photosRemoteImpl.getCuratedPhotos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getPhotosReturnsData() {
        val photoResponse = PhotoFactory.makePhotoResponse()
        stubPhotoServiceGetCuratedPhotos(Flowable.just(photoResponse))
        val photoEntities = mutableListOf<PhotoEntity>()
        photoResponse.forEach {
            photoEntities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = photosRemoteImpl.getCuratedPhotos().test()
        testObserver.assertValue(photoEntities)
    }
    //</editor-fold>

    private fun stubPhotoServiceGetCuratedPhotos(observable:
                                                Flowable<List<PhotoModel>>) {
        whenever(photoService.getCuratedPhotos(10, 10, "latest","client"))
                .thenReturn(observable)
    }
}