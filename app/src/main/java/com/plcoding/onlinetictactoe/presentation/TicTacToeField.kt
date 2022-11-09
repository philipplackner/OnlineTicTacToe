package com.plcoding.onlinetictactoe.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.onlinetictactoe.data.GameState

@Composable
fun TicTacToeField(
    state: GameState,
    modifier: Modifier = Modifier,
    playerXColor: Color = Color.Green,
    playerOColor: Color = Color.Red,
    onTapInField: (x: Int, y: Int) -> Unit
) {
    Canvas(
        modifier = modifier
            .pointerInput(true) {
                detectTapGestures {
                    val x = (3 * it.x.toInt() / size.width)
                    val y = (3 * it.y.toInt() / size.height)
                    onTapInField(x, y)
                }
            }
    ) {
        drawField()

        state.field.forEachIndexed { y, _ ->
            state.field[y].forEachIndexed { x, player ->
                val offset = Offset(
                    x = x * size.width * (1 / 3f) + size.width / 6f,
                    y = y * size.height * (1 / 3f) + size.height / 6f
                )
                if(player == 'X') {
                    drawX(
                        color = playerXColor,
                        center = offset
                    )
                } else if(player == 'O') {
                    drawO(
                        color = playerOColor,
                        center = offset
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawO(
    color: Color,
    center: Offset,
    size: Size = Size(50.dp.toPx(), 50.dp.toPx())
) {
    drawCircle(
        color = color,
        center = center,
        radius = size.width / 2f,
        style = Stroke(
            width = 3.dp.toPx()
        )
    )
}

private fun DrawScope.drawX(
    color: Color,
    center: Offset,
    size: Size = Size(50.dp.toPx(), 50.dp.toPx())
) {
    drawLine(
        color = color,
        start = Offset(
            x = center.x - size.width / 2f,
            y = center.y - size.height / 2f
        ),
        end = Offset(
            x = center.x + size.width / 2f,
            y = center.y + size.height / 2f
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )
    drawLine(
        color = color,
        start = Offset(
            x = center.x - size.width / 2f,
            y = center.y + size.height / 2f
        ),
        end = Offset(
            x = center.x + size.width / 2f,
            y = center.y - size.height / 2f
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawField() {
    // 1st vertical line
    drawLine(
        color = Color.Black,
        start = Offset(
            x = size.width * (1 / 3f),
            y = 0f
        ),
        end = Offset(
            x = size.width * (1 / 3f),
            y = size.height
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    // 2nd vertical line
    drawLine(
        color = Color.Black,
        start = Offset(
            x = size.width * (2 / 3f),
            y = 0f
        ),
        end = Offset(
            x = size.width * (2 / 3f),
            y = size.height
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    // 1st horizontal line
    drawLine(
        color = Color.Black,
        start = Offset(
            x = 0f,
            y = size.height * (1 / 3f)
        ),
        end = Offset(
            x = size.width,
            y = size.height * (1 / 3f)
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    // 2nd horizontal line
    drawLine(
        color = Color.Black,
        start = Offset(
            x = 0f,
            y = size.height * (2 / 3f)
        ),
        end = Offset(
            x = size.width,
            y = size.height * (2 / 3f)
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )
}

@Preview(showBackground = true)
@Composable
fun TicTacToeFieldPreview() {
    TicTacToeField(
        state = GameState(
            field = arrayOf(
                arrayOf('X', null, null),
                arrayOf(null, 'O', 'O'),
                arrayOf(null, 'X', null),
            ),
        ),
        onTapInField = { _, _ ->},
        modifier = Modifier.size(300.dp)
    )
}