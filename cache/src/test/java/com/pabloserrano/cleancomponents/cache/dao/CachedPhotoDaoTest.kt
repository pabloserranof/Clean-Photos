package com.pabloserrano.cleancomponents.cache.dao

import android.arch.persistence.room.Room
import com.pabloserrano.cleancomponents.cache.db.PhotosDatabase
import com.pabloserrano.cleancomponents.cache.test.factory.PhotoFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
open class CachedPhotoDaoTest {

    private lateinit var photosDatabase: PhotosDatabase

    @Before
    fun initDb() {
        photosDatabase = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.baseContext,
                PhotosDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        photosDatabase.close()
    }

    @Test
    fun insertPhotosSavesData() {
        val cachedPhoto = PhotoFactory.makeCachedPhoto()
        photosDatabase.cachedPhotoDao().insertPhoto(cachedPhoto)

        val photos = photosDatabase.cachedPhotoDao().getCuratedPhotos()
        assert(photos.isNotEmpty())
    }

    @Test
    fun getPhotosRetrievesData() {
        val cachedPhotos = PhotoFactory.makeCachedPhotoList(5)

        cachedPhotos.forEach {
            photosDatabase.cachedPhotoDao().insertPhoto(it) }

        val retrievedPhotos = photosDatabase.cachedPhotoDao().getCuratedPhotos()
        assert(retrievedPhotos == cachedPhotos.sortedWith(compareBy({ it.id }, { it.id })))
    }

}