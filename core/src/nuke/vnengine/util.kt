package nuke.vnengine

import com.badlogic.gdx.utils.Disposable
import ktx.assets.disposeSafely
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import kotlin.properties.Delegates

inline fun <T : Disposable> disposeOnWrite(crossinline beforeDispose: (oldValue: T) -> Unit,
                                           crossinline onChange: (newValue: T) -> Unit) =
        Delegates.observable<T?>(null) { _, oldValue, newValue ->
            if (oldValue != null) {
                beforeDispose(oldValue)
                oldValue.disposeSafely()
            }
            if (newValue != null) {
                onChange(newValue)
            }
        }

inline fun NodeList.toIterable() = (0 until length).map(this::item)

inline fun <T> NodeList.map(transform: (Node) -> T) = toIterable().map(transform)