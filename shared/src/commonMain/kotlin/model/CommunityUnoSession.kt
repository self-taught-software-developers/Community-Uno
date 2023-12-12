package model

data class CommunityUnoSession(
    val deck: List<Card> = emptyList(),
    val players: List<Card> = emptyList(),
    val id: String
)
