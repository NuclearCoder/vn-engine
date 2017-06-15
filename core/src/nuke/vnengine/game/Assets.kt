package nuke.vnengine.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.utils.Disposable
import ktx.assets.getValue
import ktx.assets.load
import ktx.assets.loadOnDemand
import ktx.assets.setLoader
import nuke.vnengine.script.loader.SceneLoader
import nuke.vnengine.script.loader.SceneSetLoader


/**
 * Created by NuclearCoder on 2017-06-08.
 */

private fun font(name: String, init: FreeTypeFontGenerator.FreeTypeFontParameter.() -> Unit) =
        FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
            fontFileName = name
            fontParameters.run(init)
        }

private fun scene(name: String) = SceneLoader.SceneLoaderParameters(name)

class Assets(resolver: FileHandleResolver) : Disposable {

    class Graphics(manager: AssetManager) {
        val badlogic by manager.loadOnDemand<Texture>("badlogic.jpg")

        val window by manager.load<Texture>("window.png")
        val windowArrow by manager.load<Texture>("arrow.png")
    }

    class Audio(manager: AssetManager) {
        val music by manager.loadOnDemand<Music>("music.mp3")
    }

    class Fonts(manager: AssetManager) {
        val dialog by manager.load("dialog32", font("dialog.ttf") {
             size = 32
        })
    }

    class Scripts(manager: AssetManager) {
        val test by manager.loadOnDemand("scene name.scene", scene("test.scene.xml"))
    }

    val manager = AssetManager(resolver).apply {
        // FreeType font support
        setLoader(FreeTypeFontGeneratorLoader(resolver))
        setLoader(FreetypeFontLoader(resolver))
        // script loader
        setLoader(SceneSetLoader(resolver))
        setLoader(SceneLoader(resolver))
    }

    val graphics = Graphics(manager)
    val audio = Audio(manager)
    val fonts = Fonts(manager)
    val script = Scripts(manager)

    override fun dispose() {
        manager.clear()
    }

}