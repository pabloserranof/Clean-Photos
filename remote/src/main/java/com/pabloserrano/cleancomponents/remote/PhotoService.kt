package com.pabloserrano.cleancomponents.remote

import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.remote.model.PhotoModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines the abstract methods used for interacting with the Unsplash API
 */
interface PhotoService {

    // TODO move client id to the interceptor
    @GET("photos/curated")
    fun getCuratedPhotos(@Query("page") page: Int,
                         @Query("per_page") per_page: Int,
                         @Query("order_by") order_by: String,
                         @Query("client_id") client_id: String):
            Flowable<List<PhotoModel>>
}