package com.forcharc.mafia.ui.anim

import androidx.compose.animation.*
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay

@ExperimentalAnimationApi
@Composable
fun EnterExitAnimation(
    showEnterAnimation: Boolean = true,
    showExitAnimation: Boolean = true,
    enterTransition: EnterTransition = slideInVertically(
        initialOffsetY = { -40 }
    ) + expandVertically(
        expandFrom = Alignment.Top
    ) + fadeIn(initialAlpha = 0.3f),
    exitTransition: ExitTransition = slideOutVertically() + shrinkVertically() + fadeOut(),
    content: @Composable (exit: (onExit: () -> Unit) -> Unit) -> Unit
) {
    val isVisible = remember {
        mutableStateOf(true)
    }

    val onExitFunc: MutableState<(() -> Unit)?> = remember { mutableStateOf(null) }

    LaunchedEffect(onExitFunc.value) {
        if (onExitFunc.value != null) {
            delay(AnimationConstants.DefaultDurationMillis.toLong())
            onExitFunc.value?.invoke()
        }
    }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = enterTransition,
        exit = if (showExitAnimation) exitTransition else fadeOut(0f, tween(0)),
        content = {
            content { onExit ->
                onExitFunc.value = onExit
                isVisible.value = false
            }
        },
        initiallyVisible = !showEnterAnimation
    )
}

