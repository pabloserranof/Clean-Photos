package com.pabloserrano.cleancomponents.cache.db

import android.arch.persistence.room.*
import android.content.Context
import com.pabloserrano.cleancomponents.cache.dao.CachedPhotoDao
import com.pabloserrano.cleancomponents.cache.model.CachedPhoto
import javax.inject.Inject

@Database(entities = arrayOf(CachedPhoto::class), version = 1)
abstract class PhotosDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedPhotoDao(): CachedPhotoDao

    private var INSTANCE: PhotosDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): PhotosDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            PhotosDatabase::class.java, "photos.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }

}