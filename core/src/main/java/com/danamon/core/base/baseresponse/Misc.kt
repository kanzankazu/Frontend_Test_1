package com.danamon.core.base.baseresponse

import com.google.gson.annotations.SerializedName

internal data class RawHttpError(
    @SerializedName("message") val message: String?,
    @SerializedName("messageTitle") val messageTitle: String?,
    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: Any?,
)
