package com.danamon.data.implementation.user.remote.response

import com.google.gson.annotations.SerializedName

data class JsonPlaceHolderPhotoResponse(
    @SerializedName("albumId") var albumId: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("thumbnailUrl") var thumbnailUrl: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("url") var url: String? = null,
)