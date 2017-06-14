package nuke.vnengine.script

class CharQueue(private val text: String) {

    private var cursor: Int = 0
    private val current: Char
        get() = text.elementAtOrElse(cursor) { throw NoSuchElementException("No more elements in char queue") }

    fun isEmpty() = cursor >= text.length
    fun peek() = current
    fun pop() = current.let { cursor++; it }
    fun peekOrNull() = text.elementAtOrNull(cursor)

    @Suppress("NOTHING_TO_INLINE")
    inline fun isNotEmpty() = !isEmpty()

}
