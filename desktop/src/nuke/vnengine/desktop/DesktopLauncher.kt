package nuke.vnengine.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import nuke.vnengine.VnEngine

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.resizable = false
        config.width = VnEngine.WIDTH.toInt()
        config.height = VnEngine.HEIGHT.toInt()
        LwjglApplication(VnEngine(), config)
    }
}
