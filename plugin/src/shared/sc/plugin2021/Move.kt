package sc.plugin2021

import sc.api.plugins.IMove

sealed class Move: IMove {
    abstract val piece: Piece
    abstract val position: Coordinates
    // TODO: properly implement rotation of pieces
    abstract val rotation: Int
}
