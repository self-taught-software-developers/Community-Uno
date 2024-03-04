package newmodel

sealed interface Player {

    data class Entity(
        val id: String
    ) : Player

}