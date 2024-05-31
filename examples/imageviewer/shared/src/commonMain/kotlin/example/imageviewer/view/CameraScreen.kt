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
import dev.programadorthi.routing.compose.LocalRouting
import dev.programadorthi.routing.compose.pop
import example.imageviewer.LocalImageProvider
import example.imageviewer.routing.PopResult
import kotlinx.coroutines.delay

@Composable
fun CameraScreen() {
    val imageProvider = LocalImageProvider.current
    val router = LocalRouting.current
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
