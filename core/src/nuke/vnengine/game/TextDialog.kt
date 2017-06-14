package nuke.vnengine.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Align

class TextDialog(private val assets: Assets,
                 val text: String, initialSpeed: Int,
                 val x: Float, val y: Float, val width: Float) {

    companion object {
        private const val delayBeforeSkippable = 0.1f
        private val dialogSkipKeys = listOf(Input.Keys.ENTER, Input.Keys.SPACE, Input.Keys.DPAD_CENTER)
    }

    private val buffer = StringBuilder(text.length).apply { if (text.isNotEmpty()) append(text[0]) }
    private var cursor = 1

    private val glyphLayout = GlyphLayout().apply { update() }

    // text display
    private var textAcc = 0f
    private var textDelay = 0.2f / initialSpeed.toFloat()
    private var textFinished = (cursor >= text.length)

    // skip text
    private var skipDelay = 0f

    // dialog is finished
    var finished: Boolean = false
        private set

    var textSpeed: Int = initialSpeed
        set(x) {
            field = x
            textDelay = 0.2f / x.toFloat()
        }

    @Suppress("NOTHING_TO_INLINE")
    inline private fun GlyphLayout.update() {
        setText(assets.fonts.dialog, buffer, Color.WHITE, this@TextDialog.width, Align.topLeft, true)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline private fun Input.isDialogSkipped() =
            justTouched() || dialogSkipKeys.any(this::isKeyJustPressed)

    fun draw(batch: SpriteBatch) {
        batch.draw(assets.graphics.window, 0f, 0f)
        assets.fonts.dialog.draw(batch, glyphLayout, x, y)
    }

    fun update(delta: Float) {
        if (!textFinished) {
            // text display
            textAcc += delta
            if (textAcc >= textDelay) {
                textAcc -= textDelay

                buffer.append(text[cursor++])
                glyphLayout.update()

                if (cursor >= text.length) {
                    textFinished = true
                }
            }

            // skip delay
            if (skipDelay < delayBeforeSkippable) {
                skipDelay += delta
            } else if (Gdx.input.isDialogSkipped()) {
                // test for skip
                text.slice(cursor..text.lastIndex).let(buffer::append)
                glyphLayout.update()
            }
        } else if (Gdx.input.isDialogSkipped()) {
            // test for finished
            finished = true
        }
    }

}
