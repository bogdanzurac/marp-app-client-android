package dev.bogdanzurac.marp.core.auth

data class User(
    val id: String,
    val email: String,
    val name: String?,
    val photoUrl: String?,
)