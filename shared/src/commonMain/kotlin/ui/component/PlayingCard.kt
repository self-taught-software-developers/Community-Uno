package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.gitlive.firebase.firestore.externals.collection
import model.Card

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayingCard(
    card: Card? = null,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10),
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color.Black,
    enabled: Boolean = true,
    onClick: () -> Unit = { }
) {

    Card(
        modifier = modifier.size(
            width = 120.dp,
            height = 170.dp
        ).border(borderWidth, borderColor, shape = shape),
        shape = shape,
        onClick = onClick,
        enabled = enabled
    ) {
        val color = remember { card?.color?.value ?: Color.Black }
        val text = remember { (card?.name ?: "Community Uno").uppercase() }

        Box(
            modifier = Modifier
                .padding(5.dp)
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = shape
                )
                .clip(shape = shape)
                .background(color = color)
                .padding(5.dp)
                .drawBehind {
                    drawOval(
                        color = Color.White,
                        blendMode = BlendMode.Clear
                    )
                    drawOval(
                        color = Color.Black,
                        style = Stroke(
                            width = borderWidth.value
                        )
                    )
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }

    }

}