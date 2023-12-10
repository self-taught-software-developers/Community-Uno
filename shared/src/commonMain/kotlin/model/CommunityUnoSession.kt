package model

data class CommunityUnoSession(
    val deck: List<Card> = emptyList(),
    val hand: List<Card> = emptyList(),
    val id: String
)
