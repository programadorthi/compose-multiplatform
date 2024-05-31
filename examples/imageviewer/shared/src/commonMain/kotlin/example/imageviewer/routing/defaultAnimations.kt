package example.imageviewer.routing

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import dev.programadorthi.routing.compose.popped
import dev.programadorthi.routing.core.application.ApplicationCall

internal fun AnimatedContentTransitionScope<ApplicationCall>.defaultEnterTransition(): EnterTransition {
    val multiplier = multiplier()
    return slideInHorizontally { w -> multiplier * w }
}

internal fun AnimatedContentTransitionScope<ApplicationCall>.defaultExitTransition(): ExitTransition {
    val multiplier = multiplier()
    return slideOutHorizontally { w -> multiplier * -1 * w }
}

private fun AnimatedContentTransitionScope<ApplicationCall>.multiplier(): Int {
    val previousEntry = initialState
    return if (previousEntry.popped) -1 else 1
}
