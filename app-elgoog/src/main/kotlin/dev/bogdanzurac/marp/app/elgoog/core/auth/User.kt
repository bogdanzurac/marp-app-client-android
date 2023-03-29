package dev.bogdanzurac.marp.app.elgoog.core.auth

data class User(
    val id: String,
    val email: String,
    val name: String?,
    val photoUrl: String?,
)