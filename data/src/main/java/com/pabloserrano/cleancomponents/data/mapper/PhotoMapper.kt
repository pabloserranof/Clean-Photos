package com.pabloserrano.cleancomponents.data.mapper

import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import javax.inject.Inject

/**
 * Map a [PhotoEntity] to and from a [PhotoDomain] instance when data is moving between
 * this later and the Domain layer
 */
open class PhotoMapper @Inject constructor(): Mapper<PhotoEntity, PhotoDomain> {

    /**
     * Map a [PhotoEntity] instance to a [PhotoDomain] instance
     */
    override fun mapFromEntity(type: PhotoEntity): PhotoDomain {
        return PhotoDomain(type.id,
                type.color,
                type.createdAt,
                type.description,
                type.likedByUser,
                type.url,
                type.updatedAt,
                type.width,
                type.height,
                type.likes)
    }

    /**
     * Map a [PhotoDomain] instance to a [PhotoEntity] instance
     */
    override fun mapToEntity(type: PhotoDomain): PhotoEntity {
        return PhotoEntity(type.id,
                type.color,
                type.createdAt,
                type.description,
                type.likedByUser,
                type.url,
                type.updatedAt,
                type.width,
                type.height,
                type.likes)
    }
}