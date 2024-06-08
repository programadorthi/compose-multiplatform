package example.imageviewer

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dev.programadorthi.routing.compose.canPop
import dev.programadorthi.routing.compose.pop
import example.imageviewer.routing.router
import example.imageviewer.view.ImageViewerAndroid

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageViewerAndroid()
        }
        onBackPressedDispatcher.addCallback {
            if (router.canPop) {
                router.pop()
            } else {
                finish()
            }
        }
    }

}
