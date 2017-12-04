package com.pabloserrano.cleancomponents.data.mapper

import com.pabloserrano.cleancomponents.data.test.factory.PhotoFactory
import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.pabloserrano.cleancomponents.data.model.PhotoEntity
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class PhotoMapperTest {

    private lateinit var photoMapper: PhotoMapper

    @Before
    fun setUp() {
        photoMapper = PhotoMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val photoEntity = PhotoFactory.makePhotoEntity()
        val photo = photoMapper.mapFromEntity(photoEntity)

        assertPhotoDataEquality(photoEntity, photo)
    }

    @Test
    fun mapToEntityMapsData() {
        val cachedPhoto = PhotoFactory.makePhotoDomain()
        val photoEntity = photoMapper.mapToEntity(cachedPhoto)

        assertPhotoDataEquality(photoEntity, cachedPhoto)
    }

    private fun assertPhotoDataEquality(photoEntity: PhotoEntity,
                                           photo: PhotoDomain) {
        assertEquals(photoEntity.id, photo.id)
    }

}