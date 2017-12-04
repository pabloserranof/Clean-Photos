package com.pabloserrano.cleancomponents.domain.model

data class User(
        val totalPhotos: Int? = null,
        val twitterUsername: String? = null,
        val lastName: Any? = null,
        val bio: String? = null,
        val totalLikes: Int? = null,
        val portfolioUrl: String? = null,
        val profileImage: ProfileImage? = null,
        val updatedAt: String? = null,
        val name: String? = null,
        val location: String? = null,
        val totalCollections: Int? = null,
        val links: Links? = null,
        val id: String? = null,
        val firstName: String? = null,
        val username: String? = null
)