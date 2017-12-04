package com.pabloserrano.cleancomponents.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.pabloserrano.cleancomponents.cache.db.constants.PhotoConstants
import com.pabloserrano.cleancomponents.cache.model.CachedPhoto

@Dao
abstract class CachedPhotoDao {

    @Query(PhotoConstants.QUERY_PHOTOS)
    abstract fun getCuratedPhotos(): List<CachedPhoto>

    @Query(PhotoConstants.DELETE_ALL_PHOTOS)
    abstract fun clearPhotos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPhoto(cachedPhoto: CachedPhoto)

}