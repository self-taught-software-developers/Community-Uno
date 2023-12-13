package model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id : String,
    val isActive: Boolean,
    val isAdmin: Boolean = false,
    val isCurrent: Boolean = false
)