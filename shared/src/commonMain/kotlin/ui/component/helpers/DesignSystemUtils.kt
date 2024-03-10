package ui.component.helpers

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object DesignSystemUtils {

    @Composable
    fun Modifier.drawBorderBehind() : Modifier {
        val px = with(LocalDensity.current) { StyleGuide.StrokeWidth.value.toPx() }

        return drawBehind {
            drawRoundRect(
                color = Color.Black,
                style = Stroke(px),
                cornerRadius = CornerRadius(px),
            )
        }.padding(StyleGuide.xPadding.value)
    }

    fun Modifier.cardBorder(
        borderWidth: Dp = 4.dp,
        borderColor: Color = Color.Black,
        shape: Shape = RoundedCornerShape(10)
    ) : Modifier {
        return border(
            width = borderWidth,
            color = borderColor,
            shape = shape
        )
    }
}