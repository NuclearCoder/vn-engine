package nuke.vnengine.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.utils.Disposable
import ktx.assets.*
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

    class Graphics(private val manager: AssetManager) {
        operator fun invoke(ref: String) = manager.loadOnDemand<Texture>(ref)

        val badlogic by manager.loadOnDemand<Texture>("badlogic.jpg")

        val window by manager.load<Texture>("window.png")
        val windowArrow by manager.load<Texture>("arrow.png")
    }

    class Music_(private val manager: AssetManager) {
        operator fun invoke(ref: String) = manager.loadOnDemand<Music>(ref)

        val music by manager.loadOnDemand<Music>("music.mp3")
    }

    class Fonts(private val manager: AssetManager) {
        val dialog by manager.load("dialog32", font("dialog.ttf") {
             size = 32
        })
    }

    class Scripts(private val manager: AssetManager) {
        operator fun invoke(name: String, file: String) = manager.loadOnDemand(name, scene(file))

        val test by manager.loadOnDemand("scene name", scene("test.scene.xml"))
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
    val music = Music_(manager)
    val fonts = Fonts(manager)
    val script = Scripts(manager)

    override fun dispose() {
        manager.disposeSafely()
    }

}