package nuke.vnengine.screens

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable

interface IScreen : Disposable {

    fun render(batch: SpriteBatch)
    fun update(delta: Float)

    fun show()
    fun hide()

    fun resume()
    fun pause()

}