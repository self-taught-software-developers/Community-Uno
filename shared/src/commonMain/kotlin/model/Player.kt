package model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id : String,
    val isAdmin: Boolean = false
)