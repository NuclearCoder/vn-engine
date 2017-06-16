package nuke.vnengine.game

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.assets.getResolver
import nuke.vnengine.VnEngine
import nuke.vnengine.screens.IScreen
import nuke.vnengine.script.Scene


class Game(val engine: VnEngine) : IScreen {

    private val assets = Assets(Files.FileType.Internal.getResolver())

    private var currentScene: Scene = assets.script.test

    private var backgroundImage: Texture = assets.graphics.badlogic
    private var backgroundMusic: Music = assets.music.music

    private var textDialog = SceneView(assets, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas viverra nibh ac neque facilisis interdum. Aenean ultrices, nibh et posuere rutrum, nunc enim lacinia ex, quis venenatis magna libero in libero. Maecenas metus erat, laoreet sit amet ante malesuada, rutrum scelerisque purus. Nullam facilisis diam sed libero varius viverra. Sed sit amet dapibus sapien. Vivamus non tortor ultricies, iaculis enim sit amet, volutpat tellus. Sed non egestas nisl, quis blandit erat. Integer mattis tellus metus, at egestas urna luctus et.",
            2, 10f, VnEngine.HEIGHT / 3 - 10f, VnEngine.WIDTH - 20f)

    override fun render(batch: SpriteBatch) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()

        // draw background
        batch.draw(backgroundImage, 0f, 0f, VnEngine.WIDTH, VnEngine.HEIGHT)

        // draw dialog
        textDialog.draw(batch)

        batch.end()
    }

    override fun update(delta: Float) {
        textDialog.update(delta)
    }

    override fun show() {
        backgroundMusic.play()
    }

    override fun hide() {
        backgroundMusic.pause()
    }

    override fun resume() {
        backgroundMusic.play()
    }

    override fun pause() {
        backgroundMusic.pause()
    }

    override fun dispose() {
        assets.dispose()
    }

}