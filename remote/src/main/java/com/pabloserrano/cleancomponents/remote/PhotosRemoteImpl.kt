package com.pabloserrano.cleancomponents.remote

import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import com.pabloserrano.cleancomponents.data.repository.PhotosRemote
import com.pabloserrano.cleancomponents.remote.mapper.PhotoEntityMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Photo instances. This class implements the
 * [PhotosRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PhotosRemoteImpl @Inject constructor(private val photoService: PhotoService,
                                           private val photoMapper: PhotoEntityMapper):
        PhotosRemote {

    override fun getCuratedPhotos(): Flowable<List<PhotoEntity>> {
        return photoService.getCuratedPhotos(0, 20, "latest", "30e1ed41d10055284417c4c113e9e80b41573d0e95410515c3e70b87af6c7514")
                .map{it}
                .map {
                    val entities = mutableListOf<PhotoEntity>()
                    it.forEach { entities.add(photoMapper.mapFromRemote(it)) }
                    entities
                }
    }

}