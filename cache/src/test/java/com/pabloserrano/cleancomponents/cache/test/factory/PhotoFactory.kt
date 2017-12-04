package com.pabloserrano.cleancomponents.cache.test.factory

import com.pabloserrano.cleancomponents.cache.model.CachedPhoto
import com.pabloserrano.cleancomponents.data.model.PhotoEntity

/**
 * Factory class for Photo related instances
 */
class PhotoFactory {

    companion object Factory {

        fun makeCachedPhoto(): CachedPhoto {
            return CachedPhoto( DataFactory.randomLong().toString(),
                    DataFactory.randomUuid(),
                    DataFactory.randomUuid(),
                    DataFactory.randomUuid(),
                    true,
                    "https://images.unsplash.com/photo-1508181844862-f7f79d2fc46d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=74ad411e762ec1a285c1dd3049193e38",
                    "2017-12-04T09:10:45-05:00",
                    150,
                    150,
                    5)
        }

        fun makePhotoEntity(): PhotoEntity {
            return PhotoEntity( DataFactory.randomLong().toString(),
                    DataFactory.randomUuid(),
                    DataFactory.randomUuid(),
                    DataFactory.randomUuid(),
                    true,
                    "https://images.unsplash.com/photo-1508181844862-f7f79d2fc46d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=74ad411e762ec1a285c1dd3049193e38",
                    "2017-12-04T09:10:45-05:00",
                    150,
                    150,
                    5)
        }

        fun makePhotoEntityList(count: Int): List<PhotoEntity> {
            val photoEntities = mutableListOf<PhotoEntity>()
            repeat(count) {
                photoEntities.add(makePhotoEntity())
            }
            return photoEntities
        }

        fun makeCachedPhotoList(count: Int): List<CachedPhoto> {
            val cachedPhotos = mutableListOf<CachedPhoto>()
            repeat(count) {
                cachedPhotos.add(makeCachedPhoto())
            }
            return cachedPhotos
        }
    }
}