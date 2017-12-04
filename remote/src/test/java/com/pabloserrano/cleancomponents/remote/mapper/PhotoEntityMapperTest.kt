package com.pabloserrano.cleancomponents.remote.mapper

import com.pabloserrano.cleancomponents.remote.test.factory.PhotoFactory
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
    fun mapFromRemoteMapsData() {
        val photoModel = PhotoFactory.makePhotoModel()
        val photoEntity = photoEntityMapper.mapFromRemote(photoModel)
        assertEquals(photoModel.id, photoEntity.id)
    }

}