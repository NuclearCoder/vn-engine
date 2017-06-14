package nuke.vnengine.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import ktx.assets.disposeSafely
import ktx.scene2d.textButton
import ktx.scene2d.verticalGroup
import nuke.vnengine.VnEngine

class MainMenuScreen(val engine: VnEngine) : Screen {

    private val uiSkin = Skin(Gdx.files.internal("uiskin.json"))

    private val stage = Stage(ScreenViewport()).apply(Gdx.input::setInputProcessor).also { stage ->
        stage + verticalGroup {
            setFillParent(true)

            textButton(text = "Start Game", skin = uiSkin) {
                onClick {
                    stage + (Actions.fadeOut(0.5f) then Actions.run {
                        engine.screen = engine.game
                    })
                }
                pad(4f)
            }

            textButton(text = "Exit", skin = uiSkin) {
                onClick {
                    stage + (Actions.fadeOut(0.2f) then Actions.hide() then Actions.run {
                        Gdx.app.exit()
                    })
                }
                pad(4f)
            }

            center()
            columnCenter()
            pack()
        }
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.disposeSafely()
    }

    override fun show() {
    }

    override fun hide() {
    }

    override fun resume() {
    }

    override fun pause() {
    }

}