package com.pabloserrano.cleancomponents.cache.mapper

import com.pabloserrano.cleancomponents.cache.model.CachedPhoto
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import javax.inject.Inject

/**
 * Map a [CachedPhoto] instance to and from a [PhotoEntity] instance when data is moving between
 * this later and the Data layer
 */
open class PhotoEntityMapper @Inject constructor():
        EntityMapper<CachedPhoto, PhotoEntity> {

    /**
     * Map a [PhotoEntity] instance to a [CachedPhoto] instance
     */
    override fun mapToCached(type: PhotoEntity): CachedPhoto {
        return CachedPhoto(type.id,
                type.color,
                type.createdAt,
                type.description?.toString(),
                type.likedByUser,
                type.url,
                type.updatedAt,
                type.width,
                type.height,
                type.likes)
    }

    /**
     * Map a [CachedPhoto] instance to a [PhotoEntity] instance
     */
    override fun mapFromCached(type: CachedPhoto): PhotoEntity {

        return PhotoEntity(type.id,
                type.color,
                type.createdAt,
                type.description,
                type.likedByUser,
                type.urls,
                type.updatedAt,
                type.width,
                type.height,
                type.likes)
    }

}