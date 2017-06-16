package nuke.vnengine.script.loader

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import ktx.assets.getAsset
import nuke.vnengine.script.Scene

class SceneLoader(resolver: FileHandleResolver) : SynchronousAssetLoader<Scene, SceneLoader.SceneLoaderParameters>(resolver) {

    class SceneLoaderParameters(val fileName: String) : AssetLoaderParameters<Scene>()

    override fun getDependencies(assetName: String, file: FileHandle, parameter: SceneLoaderParameters): Array<AssetDescriptor<out Any>> {
        return Array<AssetDescriptor<out Any>>(1).apply {
            add(AssetDescriptor(parameter.fileName, SceneSetLoader.classObject))
        }
    }

    override fun load(assetManager: AssetManager, assetName: String, file: FileHandle, parameter: SceneLoaderParameters): Scene? {
        val sceneSet = assetManager.getAsset<Map<String, Scene>>(parameter.fileName)

        return sceneSet[assetName]
    }


}