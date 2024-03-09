package newmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Loop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface Card {

    data class UI(
        val id: String,
        val ownerId: String,
        val properties: Properties,
        val isFaceShown: Boolean
    ) : Card {
        fun getColor() : Color {
            return if (isFaceShown) {
                properties.color.value
            } else Color.Black
        }
    }

    data class Entity(
        val id: String,
        val ownerId: String,
        val properties: Properties,
        val isPlayed: Boolean
    ) : Card

    data class Properties(
        val type: CardType,
        val color: CardColor
    )

    enum class CardColor(val value: Color) {
        RED(Color.Red),
        BLUE(Color.Blue),
        YELLOW(Color.Yellow),
        GREEN(Color.Green),
        BLACK(Color.Black)
    }

    enum class CardType(var value: CardValue) {
        NUMERIC(CardValue.Text("1..9")),
        DRAW_2(CardValue.Text("+2")),
        DRAW_4(CardValue.Text("+4")),
        WILD(CardValue.Text("Wild")),
        SKIP(CardValue.Icon(Icons.Default.Block)),
        REVERSE(CardValue.Icon(Icons.Default.Loop))
    }

    sealed interface CardValue {
        data class Text(val value: String) : CardValue
        data class Icon(val value: ImageVector) : CardValue
    }
}