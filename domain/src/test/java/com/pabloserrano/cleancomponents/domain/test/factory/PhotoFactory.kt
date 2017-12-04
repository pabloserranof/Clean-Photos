package com.pabloserrano.cleancomponents.domain.test.factory

import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import com.pabloserrano.cleancomponents.domain.test.factory.DataFactory.Factory.randomLong

/**
 * Factory class for Photo related instances
 */
class PhotoFactory {

    companion object Factory {

        fun makePhotoList(count: Int): List<PhotoDomain> {
            val photos = mutableListOf<PhotoDomain>()
            repeat(count) {
                photos.add(makePhoto())
            }
            return photos
        }

        fun makePhoto(): PhotoDomain {
            return PhotoDomain(  randomLong().toString(),
                    color = "red",
                    createdAt = "2017-11-02T15:27:27-04:00",
                    description = "nonce",
                    likedByUser = true,
                    url = "https://images.unsplash.com/photo-1508473161895-5dabcc2f59cf?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=f28ed3a387c40447cc89af3cad835779",
                    updatedAt = "2017-11-02T19:27:27-04:00",
                    width = 3788,
                    height = 2304,
                    likes = 20)
        }

    }

}