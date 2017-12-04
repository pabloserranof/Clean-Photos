package com.pabloserrano.cleancomponents.remote.model

data class PhotoModel(
        val id: String,
        val currentUserCollections: List<Any?>? = null,
        val color: String? = null,
        val createdAt: String? = null,
        val description: Any? = null,
        val likedByUser: Boolean? = null,
        val urls: Urls,
        val updatedAt: String? = null,
        val width: Int? = null,
        val links: Links? = null,
        val categories: List<Any?>? = null,
        val user: User? = null,
        val height: Int? = null,
        val likes: Int? = null
)