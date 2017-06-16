import nuke.vnengine.script.parser.ScriptParser
import java.nio.charset.Charset

fun main(args: Array<String>) {

    val string = """
<script>
<scene name="sceneA">
Bonjour c'est du texte blah       <endl/> ici un retour<endl/>
Avec un argument <img ref="badlogic.jpg" />
</scene>
<scene name="sceneB">
Blaaaaah<endl/>
ok
</scene>
</script>
"""

    string.byteInputStream(Charset.forName("UTF-8")).use {
        val scenes = ScriptParser.parse(it)
        println(scenes)
    }


}