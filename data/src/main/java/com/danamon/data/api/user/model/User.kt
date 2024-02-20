package com.danamon.data.api.user.model

data class User(
    val userId: Long = 0L,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val role: Int = -1,
)
