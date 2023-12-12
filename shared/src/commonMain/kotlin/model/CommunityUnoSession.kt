package model

data class CommunityUnoSession(
    val deck: List<Card> = emptyList(),
    val players: List<Profile> = emptyList(),
    val id: String
)
