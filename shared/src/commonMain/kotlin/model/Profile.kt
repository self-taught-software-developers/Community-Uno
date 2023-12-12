package model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id : String,
    val isActive: Boolean
)