package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.Card

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayingCard(
    card: Card,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = { }
) {

    Card(
        modifier = modifier
            .size(width = 100.dp, height = 140.dp),
        shape = shapes.medium,
        onClick = onClick,
        enabled = enabled
    ) {
        Box(
            modifier = Modifier
                .border(2.dp, Color.Black)
                .padding(5.dp)
                .border(2.dp, Color.Black)
                .fillMaxSize()
                .clip(shapes.medium)
                .background(card.color.value)
                .drawBehind {
                    drawOval(
                        color = Color.White,
                        blendMode = BlendMode.Clear
                    )
                    drawOval(
                        color = Color.Black,
                        style = Stroke(width = 2f),
                    )
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = card.name,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
            )
        }
    }

}