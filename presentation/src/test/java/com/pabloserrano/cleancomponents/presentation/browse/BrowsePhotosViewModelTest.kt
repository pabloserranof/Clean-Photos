package com.pabloserrano.cleancomponents.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.pabloserrano.cleancomponents.domain.interactor.browse.GetCuratedPhotos
import io.reactivex.subscribers.DisposableSubscriber
import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import com.pabloserrano.cleancomponents.presentation.data.ResourceState
import com.pabloserrano.cleancomponents.presentation.mapper.PhotosMapper
import com.pabloserrano.cleancomponents.presentation.model.PhotoView
import com.pabloserrano.cleancomponents.presentation.test.factory.PhotoFactory
import com.pabloserrano.cleancomponents.presentation.test.factory.DataFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

@RunWith(JUnit4::class)
class BrowsePhotosViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var getCuratedPhotos: GetCuratedPhotos
    @Mock lateinit var photosMapper: PhotosMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<PhotoDomain>>>

    private lateinit var photosViewModel: BrowsePhotosViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor<DisposableSubscriber<List<PhotoDomain>>>()
        getCuratedPhotos = mock()
        photosMapper = mock()
        photosViewModel = BrowsePhotosViewModel(getCuratedPhotos, photosMapper)
    }

    @Test
    fun getPhotosExecutesUseCase() {
        photosViewModel.getCuratedPhotos()

        verify(getCuratedPhotos, times(1)).execute(any(), anyOrNull())
    }

    //<editor-fold desc="Success">
    @Test
    fun getCuratedPhotosReturnsSuccess() {
        val list = PhotoFactory.makePhotoList(2)
        val viewList = PhotoFactory.makePhotoViewList(2)
        stubPhotoMapperMapToView(viewList[0], list[0])
        stubPhotoMapperMapToView(viewList[1], list[1])

        photosViewModel.getCuratedPhotos()

        verify(getCuratedPhotos).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assert(photosViewModel.getCuratedPhotos().value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun getCuratedPhotosReturnsDataOnSuccess() {
        val list = PhotoFactory.makePhotoList(2)
        val viewList = PhotoFactory.makePhotoViewList(2)

        stubPhotoMapperMapToView(viewList[0], list[0])
        stubPhotoMapperMapToView(viewList[1], list[1])

        photosViewModel.getCuratedPhotos()

        verify(getCuratedPhotos).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assert(photosViewModel.getCuratedPhotos().value?.data == viewList)
    }

    @Test
    fun getCuratedPhotosReturnsNoMessageOnSuccess() {
        val list = PhotoFactory.makePhotoList(2)
        val viewList = PhotoFactory.makePhotoViewList(2)

        stubPhotoMapperMapToView(viewList[0], list[0])
        stubPhotoMapperMapToView(viewList[1], list[1])

        photosViewModel.getCuratedPhotos()

        verify(getCuratedPhotos).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assert(photosViewModel.getCuratedPhotos().value?.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getCuratedPhotosReturnsError() {
        photosViewModel.getCuratedPhotos()

        verify(getCuratedPhotos).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assert(photosViewModel.getCuratedPhotos().value?.status == ResourceState.ERROR)
    }

    @Test
    fun getCuratedPhotosFailsAndContainsNoData() {
        photosViewModel.getCuratedPhotos()

        verify(getCuratedPhotos).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assert(photosViewModel.getCuratedPhotos().value?.data == null)
    }

    @Test
    fun getCuratedPhotosFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        photosViewModel.getCuratedPhotos()

        verify(getCuratedPhotos).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(photosViewModel.getCuratedPhotos().value?.message == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getCuratedPhotosReturnsLoading() {
        photosViewModel.getCuratedPhotos()

        assert(photosViewModel.getCuratedPhotos().value?.status == ResourceState.LOADING)
    }

    @Test
    fun getCuratedPhotosContainsNoDataWhenLoading() {
        photosViewModel.getCuratedPhotos()

        assert(photosViewModel.getCuratedPhotos().value?.data == null)
    }

    @Test
    fun getCuratedPhotosContainsNoMessageWhenLoading() {
        photosViewModel.getCuratedPhotos()

        assert(photosViewModel.getCuratedPhotos().value?.data == null)
    }
    //</editor-fold>

    private fun stubPhotoMapperMapToView(photoView: PhotoView,
                                         photo: PhotoDomain) {
        whenever(photosMapper.mapToView(photo))
                .thenReturn(photoView)
    }

}