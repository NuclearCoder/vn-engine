package nuke.vnengine.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.assets.disposeSafely
import nuke.vnengine.VnEngine
import nuke.vnengine.game.Game


class GameScreen(val engine: VnEngine) : Screen {

    private val viewport = FitViewport(VnEngine.WIDTH, VnEngine.HEIGHT)
    private val batch = SpriteBatch()

    var active: Boolean = false

    private val game = Game(engine)

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()

        game.render(batch)
        if (active) {
            game.update(delta)
        }

        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun show() {
        game.show()
        active = true
    }

    override fun hide() {
        game.show()
        active = false
    }

    override fun resume() {
        game.resume()
        active = true
    }

    override fun pause() {
        game.pause()
        active = false
    }

    override fun dispose() {
        game.disposeSafely()
        batch.disposeSafely()
    }

}