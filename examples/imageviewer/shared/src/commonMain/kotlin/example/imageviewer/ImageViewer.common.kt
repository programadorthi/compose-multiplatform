package example.imageviewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import dev.programadorthi.routing.compose.animation.Routing
import example.imageviewer.routing.defaultEnterTransition
import example.imageviewer.routing.defaultExitTransition
import example.imageviewer.routing.router

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

@Composable
fun ImageViewerWithProvidedDependencies() {
    Routing(
        routing = router,
        startUri = "/gallery/0",
        enterTransition = { defaultEnterTransition() },
        exitTransition = { defaultExitTransition() },
    )
}
