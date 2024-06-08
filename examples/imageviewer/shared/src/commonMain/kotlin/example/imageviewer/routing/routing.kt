package example.imageviewer.routing

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import dev.programadorthi.routing.compose.animation.composable
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
            // Check if we are routing from MemoryPage to GalleryPage
            val enterFadeIn = initialState.uri.startsWith("/memory")
            if (enterFadeIn) {
                fadeIn()
            } else {
                defaultEnterTransition()
            }
        },
        exitTransition = {
            // Check if we are routing from GalleryPage to MemoryPage
            val exitFadeOut = targetState.uri.startsWith("/memory")
            if (exitFadeOut) {
                fadeOut(tween(durationMillis = 500, 500))
            } else {
                defaultExitTransition()
            }
        }
    ) { page ->
        GalleryScreen(galleryPage = page)
    }

    composable<MemoryPage>(
        enterTransition = {
            // Check if we are routing from MemoryPage to GalleryPage
            val enterFadeIn = initialState.uri.startsWith("/gallery")
            if (enterFadeIn) {
                fadeIn()
            } else {
                defaultEnterTransition()
            }
        },
        exitTransition = {
            // Check if we are routing from GalleryPage to MemoryPage
            val exitFadeOut = targetState.uri.startsWith("/gallery")
            if (exitFadeOut) {
                fadeOut(tween(delayMillis = 150))
            } else {
                defaultExitTransition()
            }
        }
    ) { page ->
        MemoryScreen(memoryPage = page)
    }
}