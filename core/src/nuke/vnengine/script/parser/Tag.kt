package nuke.vnengine.script.parser

import nuke.vnengine.script.Frame
import org.w3c.dom.NamedNodeMap

data class Tag(val name: String, val args: NamedNodeMap) {

    fun toFrame(): Frame {
        return when (name) {
            "endl", "nl", "br" -> Frame.Text("\n")
            "nbsp", "sp" -> Frame.Text(" ")
            "background", "bg", "img" -> Frame.Background(args("ref"))
            "music", "bgm" -> Frame.Music(args("ref"))
            "sound", "sfx", "se" -> Frame.Sound(args("ref"))
            else -> throw IllegalArgumentException("Unknown tag type \"$name\"")
        }
    }

    private fun args(arg: String) =
            args.getNamedItem(arg)?.textContent ?: throw IllegalArgumentException("Required \"$arg\" attribute on \"$name\" tag")
}