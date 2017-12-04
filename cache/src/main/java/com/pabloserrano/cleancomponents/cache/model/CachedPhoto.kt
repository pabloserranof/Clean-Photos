package com.pabloserrano.cleancomponents.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.pabloserrano.cleancomponents.cache.db.constants.PhotoConstants

/**
 * Model used solely for the caching of a photo
 * TODO store custom types like the class Urls
 */
@Entity(tableName = PhotoConstants.TABLE_NAME)
data class CachedPhoto(

        @PrimaryKey
        var id: String,
        var color: String? = null,
        var createdAt: String? = null,
        var description: String? = null,
        var likedByUser: Boolean? = null,
        var urls: String,
        var updatedAt: String? = null,
        var width: Int? = null,
        var height: Int? = null,
        var likes: Int? = null)
