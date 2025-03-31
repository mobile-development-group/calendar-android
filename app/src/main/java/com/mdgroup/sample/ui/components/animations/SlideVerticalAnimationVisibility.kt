package com.mdgroup.sample.ui.components.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import kotlin.math.abs

@Composable
fun SlideVerticalAnimationVisibility(
    visible: Boolean,
    duration: Int = 350,
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { abs(it) },
            animationSpec = tween(duration)
        ) + fadeIn(initialAlpha = 0.5f, animationSpec = tween(duration)),
        exit = slideOutVertically(
            targetOffsetY = { abs(it) },
            animationSpec = tween(duration)
        ) + fadeOut(targetAlpha = 0.5f, animationSpec = tween(duration)),
        content = content
    )
}