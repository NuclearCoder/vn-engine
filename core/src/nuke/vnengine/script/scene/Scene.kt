package nuke.vnengine.script.scene

import java.util.function.Consumer


sealed class Scene : Iterable<Frame> {

    abstract val name: String
    abstract val frames: Iterable<Frame>

    data class Classic(override val name: String, override val frames: Iterable<Frame>) : Scene()

    override fun forEach(action: Consumer<in Frame>?) = frames.forEach(action)
    override fun iterator() = frames.iterator()
    override fun spliterator() = frames.spliterator()

}