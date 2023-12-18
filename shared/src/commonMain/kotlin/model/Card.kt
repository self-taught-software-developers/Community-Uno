package model

import domain.generateNumericCards
import kotlinx.datetime.Clock
import kotlinx.datetime.internal.JSJoda.LocalDateTime
import kotlinx.datetime.internal.JSJoda.ZoneId
import kotlinx.datetime.internal.JSJoda.ZoneOffset
import kotlinx.serialization.Serializable
import kotlin.math.abs
import kotlin.random.Random

@Serializable
data class Card(
    val type: CardType,
    val name: String = type.name,
    val color: CardColor,
    val ownerId: String? = null,
    val key: String = "0",
    val playedAt: Int = Clock.System.now().toEpochMilliseconds().toInt()
)