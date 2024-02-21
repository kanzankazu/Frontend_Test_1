package com.danamon.data.implementation.user.mapper

import com.danamon.core.ext.orNull
import com.danamon.core.ext.orNullObject
import com.danamon.data.api.user.model.JsonPlaceHolderPhoto
import com.danamon.data.api.user.model.User
import com.danamon.data.implementation.user.local.room.model.UserEntity
import com.danamon.data.implementation.user.remote.response.JsonPlaceHolderPhotoResponse

fun List<JsonPlaceHolderPhotoResponse?>?.toJsonPlaceHolderPhoto(): List<JsonPlaceHolderPhoto> {
    return this?.map {
        it.orNullObject(JsonPlaceHolderPhoto()) { jsonPlaceHolderPhotoResponse ->
            jsonPlaceHolderPhotoResponse.toJsonPlaceHolderPhoto()
        }
    } ?: listOf()
}

fun JsonPlaceHolderPhotoResponse.toJsonPlaceHolderPhoto() = JsonPlaceHolderPhoto(
    albumId = albumId.orNull(),
    id = id.orNull(),
    thumbnailUrl = thumbnailUrl.orNull(),
    title = title.orNull(),
    url = url.orNull()
)

fun UserEntity.toUser() = User(
    userId = userId.orNull(),
    username = username.orNull(),
    email = email.orNull(),
    password = password.orNull(),
    role = role.orNull()
)

fun User.toStringRole() = when (role) {
    1 -> "Admin"
    else -> "User"
}

