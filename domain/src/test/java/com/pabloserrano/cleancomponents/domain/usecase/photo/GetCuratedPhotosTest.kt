package com.pabloserrano.cleancomponents.domain.usecase.photo

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.domain.executor.PostExecutionThread
import com.pabloserrano.cleancomponents.domain.executor.ThreadExecutor
import com.pabloserrano.cleancomponents.domain.interactor.browse.GetCuratedPhotos
import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import com.pabloserrano.cleancomponents.domain.repository.PhotoRepository
import com.pabloserrano.cleancomponents.domain.test.factory.PhotoFactory
import org.junit.Before
import org.junit.Test

class GetCuratedPhotosTest {

    private lateinit var getCuratedPhotos: GetCuratedPhotos

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockPhotoRepository: PhotoRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockPhotoRepository = mock()
        getCuratedPhotos = GetCuratedPhotos(mockPhotoRepository, mockThreadExecutor,
                mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getCuratedPhotos.buildUseCaseObservable(null)
        verify(mockPhotoRepository).getCuratedPhotos()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubPhotoRepositoryGetPhotos(Flowable.just(PhotoFactory.makePhotoList(2)))
        val testObserver = getCuratedPhotos.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val photos = PhotoFactory.makePhotoList(2)
        stubPhotoRepositoryGetPhotos(Flowable.just(photos))
        val testObserver = getCuratedPhotos.buildUseCaseObservable(null).test()
        testObserver.assertValue(photos)
    }

    private fun stubPhotoRepositoryGetPhotos(single: Flowable<List<PhotoDomain>>) {
        whenever(mockPhotoRepository.getCuratedPhotos())
                .thenReturn(single)
    }

}