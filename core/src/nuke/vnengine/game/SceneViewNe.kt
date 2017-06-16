package nuke.vnengine.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Align
import nuke.vnengine.script.Frame
import java.util.*

class SceneViewNe(private val game: Game, private val assets: Assets,
                  private val frames: Queue<Frame>,
                  private val x: Float, private val y: Float, private val width: Float,
                  initialSpeed: Int = 2) {

    companion object {
        private val dialogSkipKeys = listOf(Input.Keys.ENTER, Input.Keys.SPACE)
    }

    private var currentFrame = frames.poll()

    private val buffer = StringBuilder(64)

    // dialog is finished
    var finished: Boolean = false
        private set

    var textSpeed: Int = initialSpeed
        set(value) {
            field = value
            textDelay = delayFor(value)
        }

    @Suppress("NOTHING_TO_INLINE")
    inline private fun Input.isDialogSkipped() =
            justTouched() || dialogSkipKeys.any(this::isKeyJustPressed)

    fun draw(batch: SpriteBatch) {
        batch.draw(assets.graphics.window, 0f, 0f)
        assets.fonts.dialog.draw(batch, glyphLayout, x, y)
    }

    fun update(delta: Float) {
        val frame = currentFrame
        when (frame) {
            is Frame.Text -> updateText(delta)

            null -> if (Gdx.input.isDialogSkipped()) {
                finished = true
            }
        }
    }

    // text display
    private val glyphLayout = GlyphLayout().apply { update() }

    private var textAcc = 0f
    private var textDelay = delayFor(initialSpeed)

    private var textFinished = false
    private var textCursor = 1
    private lateinit var currentText: String

    @Suppress("NOTHING_TO_INLINE")
    inline private fun GlyphLayout.update() =
            setText(assets.fonts.dialog, buffer, Color.WHITE, this@SceneViewNe.width, Align.topLeft, true)

    private fun delayFor(speed: Int) = 0.2f / speed.toFloat()

    private fun updateText(delta: Float) {
        // text display
        textAcc += delta
        if (textAcc >= textDelay) {
            textAcc -= textDelay

            buffer.append(currentText[textCursor++])
            glyphLayout.update()

            if (textCursor >= currentText.length) {
                textFinished = true
            }
        }

        if (Gdx.input.isDialogSkipped()) {
            // test for skip
            currentText.slice(textCursor..currentText.lastIndex).let(buffer::append)
            glyphLayout.update()
        }
    }

}
