package example.imageviewer.routing

import io.ktor.resources.Resource

interface Page

@Resource("/camera")
class CameraPage : Page

@Resource("/fullscreen/{pictureIndex}")
class FullScreenPage(val pictureIndex: Int) : Page

@Resource("/gallery/{pictureIndex}")
class GalleryPage(val pictureIndex: Int) : Page

@Resource("/memory/{pictureIndex}")
class MemoryPage(val pictureIndex: Int) : Page