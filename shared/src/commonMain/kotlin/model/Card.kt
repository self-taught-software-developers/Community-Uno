package model

import domain.generateNumericCards
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
    val uuid: String = getRandomString(12)
)

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}