package example.imageviewer.routing

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import dev.programadorthi.routing.compose.animation.composable
import dev.programadorthi.routing.compose.resource
import dev.programadorthi.routing.core.install
import dev.programadorthi.routing.core.routing
import dev.programadorthi.routing.resources.Resources
import dev.programadorthi.routing.statuspages.StatusPages
import example.imageviewer.view.CameraScreen
import example.imageviewer.view.FullscreenImageScreen
import example.imageviewer.view.GalleryScreen
import example.imageviewer.view.MemoryScreen

val router = routing {
    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            println("====== SOMETHING WENT WRONG ======")
            println("========== call info ==============")
            println(call)
            println("========== cause info =============")
            println(cause)
        }
    }

    composable<CameraPage> {
        CameraScreen()
    }

    composable<FullScreenPage> { page ->
        FullscreenImageScreen(fullScreenPage = page)
    }

    composable<GalleryPage>(
        enterTransition = {
            val previousPage = initialState.resource<MemoryPage>()
            // Check if we are navigating from MemoryPage to GalleryPage
            if (previousPage != null) {
                fadeIn()
            } else {
                defaultEnterTransition()
            }
        },
        exitTransition = {
            val nextPage = targetState.resource<MemoryPage>()
            // Check if we are navigating from GalleryPage to MemoryPage
            if (nextPage != null) {
                fadeOut(tween(delayMillis = 150))
            } else {
                defaultExitTransition()
            }
        }
    ) { page ->
        GalleryScreen(galleryPage = page)
    }

    composable<MemoryPage>(
        enterTransition = {
            val previousPage = initialState.resource<GalleryPage>()
            // Check if we are navigating from GalleryPage to MemoryPage
            if (previousPage != null) {
                fadeIn()
            } else {
                defaultEnterTransition()
            }
        },
        exitTransition = {
            val nextPage = targetState.resource<GalleryPage>()
            // Check if we are navigating from MemoryPage to GalleryPage
            if (nextPage != null) {
                fadeOut(tween(durationMillis = 500, 500))
            } else {
                defaultExitTransition()
            }
        }
    ) { page ->
        MemoryScreen(memoryPage = page)
    }
}