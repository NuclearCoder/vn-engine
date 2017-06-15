package nuke.vnengine.script

sealed class Frame {

    data class Text(val text: String) : Frame() // just text

    /*data class Speed(val speed: Int) : Frame() // change text speed
    data class Pause(val seconds: Float) : Frame() // break*/

    data class Background(val path: String) : Frame() // background image

    data class Music(val path: String) : Frame() // BGM
    data class Sound(val path: String) : Frame() // SE

}