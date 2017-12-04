package com.pabloserrano.cleancomponents.data

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.pabloserrano.cleancomponents.data.mapper.PhotoMapper
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import com.pabloserrano.cleancomponents.data.repository.PhotosDataStore
import com.pabloserrano.cleancomponents.data.source.PhotosCacheDataStore
import com.pabloserrano.cleancomponents.data.source.PhotoDataStoreFactory
import com.pabloserrano.cleancomponents.data.source.PhotosRemoteDataStore
import com.pabloserrano.cleancomponents.data.test.factory.PhotoFactory
import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PhotoDataRepositoryTest {

    private lateinit var photoDataRepository: PhotoDataRepository
    private lateinit var photoDataStoreFactory: PhotoDataStoreFactory
    private lateinit var photoMapper: PhotoMapper
    private lateinit var photosCacheDataStore: PhotosCacheDataStore
    private lateinit var photosRemoteDataStore: PhotosRemoteDataStore

    @Before
    fun setUp() {
        photoDataStoreFactory = mock()
        photoMapper = mock()
        photosCacheDataStore = mock()
        photosRemoteDataStore = mock()
        photoDataRepository = PhotoDataRepository(photoDataStoreFactory, photoMapper)
        stubPhotoDataStoreFactoryRetrieveCacheDataStore()
        stubPhotoDataStoreFactoryRetrieveRemoteDataStore()
    }

    //<editor-fold desc="Clear Photos">
    @Test
    fun clearPhotosCompletes() {
        stubPhotoCacheclearPhotos(Completable.complete())
        val testObserver = photoDataRepository.clearPhotos().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearPhotosCallsCacheDataStore() {
        stubPhotoCacheclearPhotos(Completable.complete())
        photoDataRepository.clearPhotos().test()
        verify(photosCacheDataStore).clearPhotos()
    }

    @Test
    fun clearPhotosNeverCallsRemoteDataStore() {
        stubPhotoCacheclearPhotos(Completable.complete())
        photoDataRepository.clearPhotos().test()
        verify(photosRemoteDataStore, never()).clearPhotos()
    }
    //</editor-fold>

    //<editor-fold desc="Save Photos">
    @Test
    fun savePhotosCompletes() {
        stubPhotoCachesavePhotos(Completable.complete())
        val testObserver = photoDataRepository.savePhotos(
                PhotoFactory.makePhotoList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun savePhotosCallsCacheDataStore() {
        stubPhotoCachesavePhotos(Completable.complete())
        photoDataRepository.savePhotos(PhotoFactory.makePhotoList(2)).test()
        verify(photosCacheDataStore).savePhotos(any())
    }

    @Test
    fun savePhotosNeverCallsRemoteDataStore() {
        stubPhotoCachesavePhotos(Completable.complete())
        photoDataRepository.savePhotos(PhotoFactory.makePhotoList(2)).test()
        verify(photosRemoteDataStore, never()).savePhotos(any())
    }
    //</editor-fold>

    //<editor-fold desc="Get Photos">
    @Test
    fun getCuratedPhotosCompletes() {
        stubPhotoCacheDataStoreIsCached(Single.just(true))
        stubPhotoDataStoreFactoryRetrieveDataStore(photosCacheDataStore)
        stubPhotoCacheDataStoregetCuratedPhotos(Flowable.just(
                PhotoFactory.makePhotoEntityList(2)))
        stubPhotoCachesavePhotos(Completable.complete())
        val testObserver = photoDataRepository.getCuratedPhotos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getCuratedPhotosReturnsData() {
        stubPhotoCacheDataStoreIsCached(Single.just(true))
        stubPhotoDataStoreFactoryRetrieveDataStore(photosCacheDataStore)
        stubPhotoCachesavePhotos(Completable.complete())
        val photos = PhotoFactory.makePhotoList(2)
        val photoEntities = PhotoFactory.makePhotoEntityList(2)
        photos.forEachIndexed { index, photo ->
            stubPhotoMapperMapFromEntity(photoEntities[index], photo) }
        stubPhotoCacheDataStoregetCuratedPhotos(Flowable.just(photoEntities))

        val testObserver = photoDataRepository.getCuratedPhotos().test()
        testObserver.assertValue(photos)
    }

    @Test
    fun getCuratedPhotosSavesPhotosWhenFromCacheDataStore() {
        stubPhotoDataStoreFactoryRetrieveDataStore(photosCacheDataStore)
        stubPhotoCachesavePhotos(Completable.complete())
        photoDataRepository.savePhotos(PhotoFactory.makePhotoList(2)).test()
        verify(photosCacheDataStore).savePhotos(any())
    }

    @Test
    fun getCuratedPhotosNeverSavesPhotosWhenFromRemoteDataStore() {
        stubPhotoDataStoreFactoryRetrieveDataStore(photosRemoteDataStore)
        stubPhotoCachesavePhotos(Completable.complete())
        photoDataRepository.savePhotos(PhotoFactory.makePhotoList(2)).test()
        verify(photosRemoteDataStore, never()).savePhotos(any())
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubPhotoCachesavePhotos(completable: Completable) {
        whenever(photosCacheDataStore.savePhotos(any()))
                .thenReturn(completable)
    }

    private fun stubPhotoCacheDataStoreIsCached(single: Single<Boolean>) {
        whenever(photosCacheDataStore.isCached())
                .thenReturn(single)
    }

    private fun stubPhotoCacheDataStoregetCuratedPhotos(single: Flowable<List<PhotoEntity>>) {
        whenever(photosCacheDataStore.getCuratedPhotos())
                .thenReturn(single)
    }

    private fun stubPhotoRemoteDataStoregetCuratedPhotos(single: Flowable<List<PhotoEntity>>) {
        whenever(photosRemoteDataStore.getCuratedPhotos())
                .thenReturn(single)
    }

    private fun stubPhotoCacheclearPhotos(completable: Completable) {
        whenever(photosCacheDataStore.clearPhotos())
                .thenReturn(completable)
    }

    private fun stubPhotoDataStoreFactoryRetrieveCacheDataStore() {
        whenever(photoDataStoreFactory.retrieveCacheDataStore())
                .thenReturn(photosCacheDataStore)
    }

    private fun stubPhotoDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(photoDataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(photosCacheDataStore)
    }

    private fun stubPhotoDataStoreFactoryRetrieveDataStore(dataStore: PhotosDataStore) {
        whenever(photoDataStoreFactory.retrieveDataStore(any()))
                .thenReturn(dataStore)
    }

    private fun stubPhotoMapperMapFromEntity(photoEntity: PhotoEntity,
                                             photo: PhotoDomain) {
        whenever(photoMapper.mapFromEntity(photoEntity))
                .thenReturn(photo)
    }
    //</editor-fold>

}