package com.pabloserrano.cleancomponents.data.model

data class PhotoEntity(
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