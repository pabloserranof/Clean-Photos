package com.pabloserrano.cleancomponents.remote.mapper

import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import com.pabloserrano.cleancomponents.remote.model.PhotoModel
import javax.inject.Inject

/*
* Map a [PhotoModel] to and from a [PhotoEntity] instance when data is moving between
* this later and the Data layer
*/
open class PhotoEntityMapper @Inject constructor() : EntityMapper<PhotoModel, PhotoEntity> {
    override fun mapFromRemote(type: PhotoModel): PhotoEntity {
        return PhotoEntity(type.id,
                type.color,
                type.createdAt,
                type.description,
                type.likedByUser,
                type.urls.regular,
                type.updatedAt,
                type.width,
                type.height,
                type.likes)
    }
}
