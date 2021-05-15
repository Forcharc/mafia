package com.forcharc.mafia.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.forcharc.mafia.ui.viewModels.PlayersViewModel
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun GetRolesScreen(
    viewModel: PlayersViewModel,
    navController: NavHostController,
    exit: (onExit: () -> Unit) -> Unit
) {

    SwipableCard()
    //SwipeToUnlock()
}

@ExperimentalMaterialApi
@Composable
fun SwipeToUnlock() {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val swipeState = rememberSwipeableState(0)
        val swipeButtonWidth = 70.dp
        val swipeButtonHeight = 70.dp

        val sizePx = with(LocalDensity.current) { (maxWidth - swipeButtonWidth).toPx() }
        val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .swipeable(
                    swipeState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.95f) },
                    orientation = Orientation.Horizontal
                )
        ) {

            Card(
                modifier = Modifier
                    .width(swipeButtonWidth)
                    .height(swipeButtonHeight)
                    .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
            ) {
                Icon(Icons.Rounded.ArrowForward, contentDescription = "Swipe button")
            }
        }

    }
}

@ExperimentalAnimationApi
@Composable
private fun SwipableCard() {
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    val animatedOffset =
        animateOffsetAsState(targetValue = offset)

    var isCardPressed by remember { mutableStateOf(false) }
    var isCardVisible by remember { mutableStateOf(true) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isCardPressed = true },
                    onDragEnd = { isCardPressed = false })
                { change, dragAmount ->
                    change.consumeAllChanges()
                    offset += dragAmount
                }
            },
        contentAlignment = Alignment.Center
    ) {
        val widthPx = LocalDensity.current.run { maxWidth.toPx() }
        val heightPx = LocalDensity.current.run { maxHeight.toPx() }

        LaunchedEffect(isCardPressed) {
            if (!isCardPressed) {
                when {
                    abs(offset.x) >= (0.25 * widthPx) -> {
                        if (offset.x > 0) {
                            offset = Offset(2 * widthPx, 0f)
                        } else {
                            offset = Offset(-2 * widthPx, 0f)
                        }
                        makeNewCardAppear({ newIsCardVisible ->
                            isCardVisible = newIsCardVisible
                        }) { newOffset: Offset -> offset = newOffset }
                    }
                    abs(offset.y) >= (0.25 * heightPx) -> {
                        if (offset.y > 0) {
                            offset = Offset(0f, 2 * heightPx)
                        } else {
                            offset = Offset(0f, -2 * heightPx)
                        }
                        makeNewCardAppear({ newIsCardVisible ->
                            isCardVisible = newIsCardVisible
                        }) { newOffset: Offset -> offset = newOffset }
                    }
                    else -> {
                        offset = Offset(0f, 0f)
                    }
                }
            }
        }


        AnimatedVisibility(
            visible = isCardVisible,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .offset {
                    IntOffset(
                        animatedOffset.value.x.toInt(),
                        animatedOffset.value.y.toInt()
                    )
                }) {
            Card(

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Privet")
                }
            }
        }
    }
}

private suspend fun makeNewCardAppear(
    onNewCardVisibility: (isVisible: Boolean) -> Unit,
    onNewOffset: (offset: Offset) -> Unit
) {
    onNewCardVisibility(false)
    delay(AnimationConstants.DefaultDurationMillis.toLong())
    onNewOffset(Offset(0f, 0f))
    delay(AnimationConstants.DefaultDurationMillis.toLong())
    onNewCardVisibility(true)
}