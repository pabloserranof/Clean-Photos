package com.pabloserrano.cleancomponents.cache.mapper

import com.pabloserrano.cleancomponents.cache.model.CachedPhoto
import com.pabloserrano.cleancomponents.cache.test.factory.PhotoFactory
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class PhotoEntityMapperTest {

    private lateinit var photoEntityMapper: PhotoEntityMapper

    @Before
    fun setUp() {
        photoEntityMapper = PhotoEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val photoEntity = PhotoFactory.makePhotoEntity()
        val cachedPhoto = photoEntityMapper.mapToCached(photoEntity)

        assertPhotoDataEquality(photoEntity, cachedPhoto)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedPhoto = PhotoFactory.makeCachedPhoto()
        val photoEntity = photoEntityMapper.mapFromCached(cachedPhoto)

        assertPhotoDataEquality(photoEntity, cachedPhoto)
    }

    private fun assertPhotoDataEquality(photoEntity: PhotoEntity,
                                        cachedPhoto: CachedPhoto) {
        assertEquals(photoEntity.id, cachedPhoto.id)
        assertEquals(photoEntity.url, cachedPhoto.urls)
    }

}