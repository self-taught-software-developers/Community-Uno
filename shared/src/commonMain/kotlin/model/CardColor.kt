package model

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

enum class CardColor(val value: Color) {
    RED(Color.Red),
    GREEN(Color.Green),
    YELLOW(Color.Yellow),
    BLUE(Color.Blue),
    BLACK(Color.Black)
}