package nuke.vnengine

import com.badlogic.gdx.Game
import nuke.vnengine.game.GameScreen
import nuke.vnengine.screens.MainMenuScreen


class VnEngine : Game() {

    companion object {
        const val WIDTH = 1024f
        const val HEIGHT = 576f
    }

    lateinit var mainMenu: MainMenuScreen
    lateinit var game: GameScreen

    override fun create() {
        mainMenu = MainMenuScreen(this)
        game = GameScreen(this)

        setScreen(game)
    }

}