package com.danamon.data.implementation.user.remote.api

import com.danamon.data.implementation.user.remote.response.JsonPlaceHolderPhotoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("https://jsonplaceholder.typicode.com/photos?_page=1&_limit=10")
    suspend fun jsonPlaceHolderPhoto(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int = 10,
    ): List<JsonPlaceHolderPhotoResponse?>?
}
