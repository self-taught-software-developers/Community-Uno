package newmodel

sealed interface Player {

    data class UI(
        val name: String,
        val hand: List<Card.UI>
    ) : Player

    data class Entity(
        val id: String
    ) : Player

}