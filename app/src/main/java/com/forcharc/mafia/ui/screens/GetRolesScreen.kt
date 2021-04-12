package com.forcharc.mafia.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import kotlinx.coroutines.delay
import kotlin.math.abs

@ExperimentalAnimationApi
@Composable
fun GetRolesScreen(navController: NavHostController, exit: (onExit: () -> Unit) -> Unit) {
    SwipableCard()
}

@ExperimentalAnimationApi
@Composable
private fun SwipableCard() {
    val offset = remember { mutableStateOf(Offset(0f, 0f)) }
    val animatedOffset =
        animateOffsetAsState(targetValue = offset.value)

    val isCardPressed = remember { mutableStateOf(false) }
    val isCardVisible = remember {
        mutableStateOf(true)
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isCardPressed.value = true },
                    onDragEnd = { isCardPressed.value = false })
                { change, dragAmount ->
                    change.consumeAllChanges()
                    offset.value += dragAmount
                }
            },
        contentAlignment = Alignment.Center
    ) {
        val widthPx = LocalDensity.current.run { maxWidth.toPx() }
        val heightPx = LocalDensity.current.run { maxHeight.toPx() }

        LaunchedEffect(isCardPressed.value) {
            if (!isCardPressed.value) {
                when {
                    abs(offset.value.x) >= (0.25 * widthPx) -> {
                        if (offset.value.x > 0) {
                            offset.value = Offset(2 * widthPx, 0f)
                        } else {
                            offset.value = Offset(-2 * widthPx, 0f)
                        }
                        makeNewCardAppear(isCardVisible, offset)
                    }
                    abs(offset.value.y) >= (0.25 * heightPx) -> {
                        if (offset.value.y > 0) {
                            offset.value = Offset(0f, 2 * heightPx)
                        } else {
                            offset.value = Offset(0f, -2 * heightPx)
                        }
                        makeNewCardAppear(isCardVisible, offset)
                    }
                    else -> {
                        offset.value = Offset(0f, 0f)
                    }
                }
            }
        }


        AnimatedVisibility(
            visible = isCardVisible.value,
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
    isCardVisible: MutableState<Boolean>,
    offset: MutableState<Offset>
) {
    isCardVisible.value = false
    delay(AnimationConstants.DefaultDurationMillis.toLong())
    offset.value = Offset(0f, 0f)
    delay(AnimationConstants.DefaultDurationMillis.toLong())
    isCardVisible.value = true
}