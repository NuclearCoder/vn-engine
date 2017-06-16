package nuke.vnengine.script

import java.util.*


sealed class Scene {

    abstract val name: String
    abstract val frames: Queue<Frame>

    data class Classic(override val name: String, override val frames: Queue<Frame>) : Scene()

}