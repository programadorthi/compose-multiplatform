package example.imageviewer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.core.stack.StackEvent
import dev.programadorthi.routing.voyager.VoyagerRouting
import example.imageviewer.routing.router
import example.imageviewer.view.GalleryScreen
import example.imageviewer.view.MemoryScreen

enum class ExternalImageViewerEvent {
    Next,
    Previous,
}

@Composable
fun ImageViewerCommon(
    dependencies: Dependencies
) {
    CompositionLocalProvider(
        LocalLocalization provides dependencies.localization,
        LocalNotification provides dependencies.notification,
        LocalImageProvider provides dependencies.imageProvider,
        LocalInternalEvents provides dependencies.externalEvents,
        LocalSharePicture provides dependencies.sharePicture,
        LocalImagesProvider provides dependencies.pictures,
    ) {
        ImageViewerWithProvidedDependencies()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ImageViewerWithProvidedDependencies() {
    VoyagerRouting(
        routing = router,
        initialScreen = GalleryScreen(initialPictureIndex = 0)
    ) { navigator ->
        AnimatedContent(
            targetState = navigator.lastItem,
            transitionSpec = {
                if (initialState is GalleryScreen && targetState is MemoryScreen) {
                    fadeIn() with fadeOut(tween(durationMillis = 500, 500))
                } else if (initialState is MemoryScreen && targetState is GalleryScreen) {
                    fadeIn() with fadeOut(tween(delayMillis = 150))
                } else {
                    val multiplier = when (navigator.lastEvent) {
                        StackEvent.Pop,
                        StackEvent.Replace -> -1

                        else -> 1
                    }
                    slideInHorizontally { w -> multiplier * w } with
                            slideOutHorizontally { w -> multiplier * -1 * w }
                }
            }) { screen ->
            screen.Content()
        }
    }
}
