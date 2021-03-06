package sc.shared

import kotlin.RuntimeException

/**
 * This exception is thrown if the server tried to create an invalid game state.
 * This indicates an error in the server or plugin code. It should never be
 * thrown on invalid input to the server (i.e. invalid move made).
 */
class InvalidGameStateException(reason: String): RuntimeException(reason)
