package com.pabloserrano.cleancomponents.cache

import android.arch.persistence.room.Room
import com.pabloserrano.cleancomponents.cache.db.PhotosDatabase
import com.pabloserrano.cleancomponents.cache.mapper.PhotoEntityMapper
import com.pabloserrano.cleancomponents.cache.model.CachedPhoto
import com.pabloserrano.cleancomponents.cache.test.factory.PhotoFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class PhotosCacheImplTest {

    private var photosDatabase = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application,
            PhotosDatabase::class.java).allowMainThreadQueries().build()
    private var entityMapper = PhotoEntityMapper()
    private var preferencesHelper = PreferencesHelper(RuntimeEnvironment.application)


    private val databaseHelper = PhotosCacheImpl(photosDatabase,
            entityMapper, preferencesHelper)

    @Test
    fun clearTablesCompletes() {
        val testObserver = databaseHelper.clearPhotos().test()
        testObserver.assertComplete()
    }

    //<editor-fold desc="Save Photos">
    @Test
    fun savePhotosCompletes() {
        val photoEntities = PhotoFactory.makePhotoEntityList(2)

        val testObserver = databaseHelper.savePhotos(photoEntities).test()
        testObserver.assertComplete()
    }

    @Test
    fun savePhotosSavesData() {
        val photoCount = 2
        val photoEntities = PhotoFactory.makePhotoEntityList(photoCount)

        databaseHelper.savePhotos(photoEntities).test()
        checkNumRowsInPhotosTable(photoCount)
    }
    //</editor-fold>

    //<editor-fold desc="Get Photos">
    @Test
    fun getPhotosCompletes() {
        val testObserver = databaseHelper.getCuratedPhotos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getPhotosReturnsData() {
        val photoEntities = PhotoFactory.makePhotoEntityList(2)
        val cachedPhotos = mutableListOf<CachedPhoto>()
        photoEntities.forEach {
            cachedPhotos.add(entityMapper.mapToCached(it))
        }
        insertPhotos(cachedPhotos)

        val testObserver = databaseHelper.getCuratedPhotos().test()
        testObserver.assertValue(photoEntities)
    }
    //</editor-fold>

    private fun insertPhotos(cachedPhotos: List<CachedPhoto>) {
        cachedPhotos.forEach {
            photosDatabase.cachedPhotoDao().insertPhoto(it)
        }
    }

    private fun checkNumRowsInPhotosTable(expectedRows: Int) {
        val numberOfRows = photosDatabase.cachedPhotoDao().getCuratedPhotos().size
        assertEquals(expectedRows, numberOfRows)
    }

}