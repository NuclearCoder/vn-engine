package nuke.vnengine.screens

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.assets.getResolver
import nuke.vnengine.VnEngine
import nuke.vnengine.game.Assets
import nuke.vnengine.game.TextDialog
import nuke.vnengine.script.scene.Scene


class GameScreen(val engine: VnEngine) : Screen {

    private val viewport = FitViewport(VnEngine.WIDTH, VnEngine.HEIGHT)
    private val batch = SpriteBatch()
    private var active: Boolean = false

    private val assets = Assets(Files.FileType.Internal.getResolver())

    private lateinit var currentScene: Scene

    private var backgroundImage: Texture = assets.graphics.badlogic
    private var backgroundMusic: Music = assets.audio.music

    private var textDialog = TextDialog(assets, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas viverra nibh ac neque facilisis interdum. Aenean ultrices, nibh et posuere rutrum, nunc enim lacinia ex, quis venenatis magna libero in libero. Maecenas metus erat, laoreet sit amet ante malesuada, rutrum scelerisque purus. Nullam facilisis diam sed libero varius viverra. Sed sit amet dapibus sapien. Vivamus non tortor ultricies, iaculis enim sit amet, volutpat tellus. Sed non egestas nisl, quis blandit erat. Integer mattis tellus metus, at egestas urna luctus et.",
            2, 10f, VnEngine.HEIGHT / 3 - 10f, VnEngine.WIDTH - 20f)

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()

        // draw background
        batch.draw(backgroundImage, 0f, 0f, VnEngine.WIDTH, VnEngine.HEIGHT)

        // draw dialog
        textDialog.draw(batch)
        if (active) textDialog.update(delta)

        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun show() {
        backgroundMusic.play()
        active = true
    }

    override fun hide() {
        backgroundMusic.pause()
        active = false
    }

    override fun resume() {
        backgroundMusic.play()
        active = true
    }

    override fun pause() {
        backgroundMusic.pause()
        active = false
    }

    override fun dispose() {
        assets.dispose()
    }

}