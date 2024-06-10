package example.imageviewer.routing

import dev.programadorthi.routing.core.install
import dev.programadorthi.routing.core.routing
import dev.programadorthi.routing.resources.Resources
import dev.programadorthi.routing.statuspages.StatusPages
import dev.programadorthi.routing.voyager.screen
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

    screen<CameraPage> {
        CameraScreen()
    }

    screen<FullScreenPage> { page ->
        FullscreenImageScreen(pictureIndex = page.pictureIndex)
    }

    screen<GalleryPage> { page ->
        GalleryScreen(initialPictureIndex = page.pictureIndex)
    }

    screen<MemoryPage> { page ->
        MemoryScreen(pictureIndex = page.pictureIndex)
    }
}