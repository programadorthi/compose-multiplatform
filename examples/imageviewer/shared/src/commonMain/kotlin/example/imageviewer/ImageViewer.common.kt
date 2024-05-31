package example.imageviewer

import androidx.compose.runtime.*
import dev.programadorthi.routing.compose.animation.Routing
import dev.programadorthi.routing.compose.pop
import example.imageviewer.routing.GalleryPage
import example.imageviewer.routing.defaultEnterTransition
import example.imageviewer.routing.defaultExitTransition
import example.imageviewer.routing.router
import example.imageviewer.view.*

enum class ExternalImageViewerEvent {
    Next,
    Previous,
    ReturnBack,
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
        enterTransition = { defaultEnterTransition() },
        exitTransition = { defaultExitTransition() },
        initial = {
            GalleryScreen(galleryPage = GalleryPage(pictureIndex = 0))
        }
    )

    val externalEvents = LocalInternalEvents.current
    LaunchedEffect(Unit) {
        externalEvents.collect {
            if (it == ExternalImageViewerEvent.ReturnBack) {
                router.pop()
            }
        }
    }
}
