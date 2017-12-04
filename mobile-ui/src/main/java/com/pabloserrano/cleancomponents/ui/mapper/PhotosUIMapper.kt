package com.pabloserrano.cleancomponents.ui.mapper

import com.pabloserrano.cleancomponents.presentation.model.PhotoView
import com.pabloserrano.cleancomponents.ui.model.PhotoViewModel
import javax.inject.Inject

/**
 * Map a [PhotoView] to and from a [PhotoViewModel] instance when data is moving between
 * this layer and the Domain layer
 */
open class PhotosUIMapper @Inject constructor(): Mapper<PhotoViewModel, PhotoView> {

    /**
     * Map a [PhotoView] instance to a [PhotoViewModel] instance
     */
    override fun mapToViewModel(type: PhotoView): PhotoViewModel {
        return PhotoViewModel(type.id,
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