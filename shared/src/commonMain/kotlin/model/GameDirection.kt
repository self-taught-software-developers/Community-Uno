package model

enum class GameDirection(val step: Int) {
    COUNTER_CLOCKWISE(-1),
    CLOCKWISE(1);

    fun opposite() : GameDirection {
        return if (this == CLOCKWISE) COUNTER_CLOCKWISE else CLOCKWISE
    }
    companion object {
        fun Player.next(direction: GameDirection, players: List<Player>): Player {
            val mod: (Int, Int) -> Int = { n, d -> ((n % d) + d) % d }
            return players[mod(players.indexOf(this) + direction.step, players.size)]
        }
    }
}

enum class Direction {
    N, E, S, W;

//    fun turned(direction: GameDirection): Direction {
//        val mod: (Int, Int) -> Int = { n, d -> ((n % d) + d) % d }
//        return values()[mod(values().indexOf(this) + turn.step, values().size)]
//    }
}