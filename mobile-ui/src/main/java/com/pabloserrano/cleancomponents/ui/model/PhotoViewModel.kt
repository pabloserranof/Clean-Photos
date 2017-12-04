package com.pabloserrano.cleancomponents.ui.model

/**
 * Representation for a [PhotoViewModel] fetched from an external layer data source
 */
class PhotoViewModel(val id: String,
                     val color: String? = null,
                     val createdAt: String? = null,
                     val description: Any? = null,
                     val likedByUser: Boolean? = null,
                     val urls: String,
                     val updatedAt: String? = null,
                     val width: Int? = null,
                     val height: Int? = null,
                     val likes: Int? = null)