package com.pabloserrano.cleancomponents.domain.interactor.browse

import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.domain.executor.PostExecutionThread
import com.pabloserrano.cleancomponents.domain.executor.ThreadExecutor
import com.pabloserrano.cleancomponents.domain.interactor.FlowableUseCase
import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import com.pabloserrano.cleancomponents.domain.repository.PhotoRepository
import javax.inject.Inject

/**
 * Created by developer on 25/11/17.
 */
open class GetCuratedPhotos @Inject constructor(val photoRepository: PhotoRepository,
                                                threadExecutor: ThreadExecutor,
                                                postExecutionThread: PostExecutionThread):
        FlowableUseCase<List<PhotoDomain>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Flowable<List<PhotoDomain>> {
        return photoRepository.getCuratedPhotos()
    }
}