package nuke.vnengine.script.parser

import nuke.vnengine.map
import nuke.vnengine.script.Frame
import nuke.vnengine.script.Scene
import org.w3c.dom.Node
import java.io.InputStream
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

/* Script format is:

#scene name
Text <tag arg="value" arg2="value"> Text

#scene...
...

 */

object ScriptParser {

    private val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

    fun parse(inputStream: InputStream): Map<String, Scene> {
        val document = documentBuilder.parse(inputStream)
        val root = document.documentElement
        if (root.tagName != "script") error("Required root \"script\" tag")
        root.normalize()

        val sceneList = root.getElementsByTagName("scene").map(this::parseScene)
        val scenes = LinkedHashMap<String, Scene>(sceneList.size).apply {
            sceneList.forEach { put(it.name, it) }
        }

        return scenes
    }

    private fun parseScene(scene: Node): Scene {
        val name = scene.attributes.getNamedItem("name").textContent ?: error("Required \"name\" attribute on \"scene\" element")

        val rawFrames = scene.childNodes.map {
            when (it.nodeType) {
                Node.TEXT_NODE -> Frame.Text(it.textContent.cleanWhitespaces())
                Node.ELEMENT_NODE -> Tag(it.nodeName, it.attributes).toFrame()
                else -> error("Illegal node type in a scene node")
            }
        }

        val frames = rawFrames.simplify()

        return Scene.Classic(name, frames)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline private fun error(msg: String): Nothing = throw IllegalStateException(msg)

    @Suppress("NOTHING_TO_INLINE")
    inline private fun String.cleanWhitespaces() =
            this.replace("\n", "").replace("\\s+".toRegex(), " ").replace("\\s$".toRegex(), "")

    private fun List<Frame>.simplify(): Queue<Frame> {
        val frames = LinkedList<Frame>()

        iterator().takeIf(Iterator<Frame>::hasNext)?.let {
            var lastFrame = it.next()
            while (it.hasNext()) {
                val last = lastFrame
                val next = it.next()
                when {
                    last is Frame.Text && next is Frame.Text -> lastFrame = Frame.Text(last.text + next.text)
                    else -> {
                        frames.addLast(last)
                        lastFrame = next
                    }
                }
            }
            frames.addLast(lastFrame)
        }

        return frames
    }

}