package com.pabloserrano.cleancomponents.presentation.model

/**
 * Representation for a [PhotoView] instance for this layers Model representation
 */
class PhotoView(
        val id: String,
        val color: String? = null,
        val createdAt: String? = null,
        val description: Any? = null,
        val likedByUser: Boolean? = null,
        val url: String,
        val updatedAt: String? = null,
        val width: Int? = null,
        val height: Int? = null,
        val likes: Int? = null)