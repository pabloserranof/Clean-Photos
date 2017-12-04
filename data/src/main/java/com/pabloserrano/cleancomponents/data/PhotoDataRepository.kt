package com.pabloserrano.cleancomponents.data

import io.reactivex.Completable
import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.data.mapper.PhotoMapper
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import com.pabloserrano.cleancomponents.data.source.PhotoDataStoreFactory
import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import com.pabloserrano.cleancomponents.domain.repository.PhotoRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PhotoRepository] interface for communicating to and from
 * data sources
 */
class PhotoDataRepository @Inject constructor(private val factory: PhotoDataStoreFactory,
                                              private val photoMapper: PhotoMapper):
        PhotoRepository {
    override fun clearPhotos(): Completable {
        return factory.retrieveCacheDataStore().clearPhotos()
    }

    override fun getCuratedPhotos(): Flowable<List<PhotoDomain>> {
        return factory.retrieveCacheDataStore().isCached()
                .flatMapPublisher {
                    factory.retrieveDataStore(it).getCuratedPhotos()
                }
                .flatMap {
                    Flowable.just(it.map { photoMapper.mapFromEntity(it) })
                }
                .flatMap {
                    savePhotos(it).toSingle { it }.toFlowable()
                }
    }

    override fun savePhotos(photos: List<PhotoDomain>): Completable {
        val photoEntities = mutableListOf<PhotoEntity>()
        photos.map { photoEntities.add(photoMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().savePhotos(photoEntities)
    }

}