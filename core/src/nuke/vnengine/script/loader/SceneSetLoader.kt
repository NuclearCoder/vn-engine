package nuke.vnengine.script.loader

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import nuke.vnengine.script.Scene
import nuke.vnengine.script.parser.ScriptParser

class SceneSetLoader(resolver: FileHandleResolver) : SynchronousAssetLoader<Map<String, Scene>, SceneSetLoader.SceneSetLoaderParameters>(resolver) {

    companion object {
        val classObject = (mapOf<String, Scene>())::class
    }

    class SceneSetLoaderParameters : AssetLoaderParameters<Map<String, Scene>>()

    override fun getDependencies(fileName: String, file: FileHandle, parameter: SceneSetLoaderParameters?): Array<AssetDescriptor<Any>>? {
        return null
    }

    override fun load(assetManager: AssetManager, fileName: String, file: FileHandle, parameter: SceneSetLoaderParameters?): Map<String, Scene> {
        val parser = file.read().use(::ScriptParser)
        val sceneSet = parser.parse()

        return sceneSet
    }


}