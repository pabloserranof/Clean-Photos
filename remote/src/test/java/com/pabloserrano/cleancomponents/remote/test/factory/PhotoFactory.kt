package com.pabloserrano.cleancomponents.remote.test.factory

import com.pabloserrano.cleancomponents.remote.model.Links
import com.pabloserrano.cleancomponents.remote.model.PhotoModel
import com.pabloserrano.cleancomponents.remote.test.factory.DataFactory.Factory.randomLong
import com.pabloserrano.cleancomponents.remote.model.Urls

/**
 * Factory class for Photo related instances
 */
class PhotoFactory {

    companion object Factory {

        fun makePhotoResponse(): List<PhotoModel> {
            val photoList = makePhotoModelList(5)
            return photoList
        }

        fun makePhotoModelList(count: Int): List<PhotoModel> {
            val photoEntities = mutableListOf<PhotoModel>()
            repeat(count) {
                photoEntities.add(makePhotoModel())
            }
            return photoEntities
        }

        fun makePhotoModel(): PhotoModel {
            return PhotoModel(
                    randomLong().toString(),
                    currentUserCollections = null,
                    color = "red",
                    createdAt = "2017-11-02T15:27:27-04:00",
                    description = "nonce",
                    likedByUser = true,
                    urls = makeUrls(),
                    updatedAt = "2017-11-02T19:27:27-04:00",
                    width = 3788,
                    links = makeLinks(),
                    categories = null,
                    user = null,
                    height = 2304,
                    likes = 20)
        }

        fun makeUrls(): Urls {
            return Urls("\"https://images.unsplash.com/photo-1509650840259-72cd4b94cc3b",
                    "https://images.unsplash.com/photo-1509650840259-72cd4b94cc3b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=6428cba2f536894d38bd397c36c31426",
                    "https://images.unsplash.com/photo-1509650840259-72cd4b94cc3b",
                    "https://images.unsplash.com/photo-1509650840259-72cd4b94cc3b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=a00bb14be7bbedfe7143926a578c332b",
                    "https://images.unsplash.com/photo-1509650840259-72cd4b94cc3b?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=497233e4dd8ba126b4f5c5b398d7cae4")
        }

        fun makeLinks(): Links {
            return Links("", "", "", "", "", "", "")
        }
    }

}