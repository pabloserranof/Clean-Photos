package com.pabloserrano.cleancomponents.presentation.mapper

import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import com.pabloserrano.cleancomponents.presentation.model.PhotoView
import javax.inject.Inject

/**
 * Map a [PhotoView] to and from a [PhotoDomain] instance when data is moving between
 * this layer and the Domain layer
 */
open class PhotosMapper @Inject constructor() : Mapper<PhotoView, PhotoDomain> {

    /**
     * Map a [PhotoDomain] instance to a [PhotoView] instance
     */
    override fun mapToView(type: PhotoDomain): PhotoView {
        return PhotoView(type.id,
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