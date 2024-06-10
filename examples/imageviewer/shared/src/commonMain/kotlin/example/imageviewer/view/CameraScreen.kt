package example.imageviewer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import dev.programadorthi.routing.voyager.LocalVoyagerRouting
import dev.programadorthi.routing.voyager.pop
import example.imageviewer.LocalImageProvider
import example.imageviewer.routing.PopResult
import kotlinx.coroutines.delay

class CameraScreen : Screen {

    @Composable
    override fun Content() {
        val imageProvider = LocalImageProvider.current
        val router = LocalVoyagerRouting.current
        var showCamera by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            if (!showCamera) {
                delay(300) // for animation
                showCamera = true
            }
        }
        Box(Modifier.fillMaxSize().background(Color.Black)) {
            if (showCamera) {
                CameraView(Modifier.fillMaxSize(), onCapture = { picture, image ->
                    imageProvider.saveImage(picture, image)
                    router.pop(result = PopResult(index = 0))
                })
            }
            TopLayout(
                alignLeftContent = {
                    BackButton {
                        router.pop()
                    }
                },
                alignRightContent = {},
            )
        }
    }
}
