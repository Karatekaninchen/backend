package sc.plugin2020

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.annotations.XStreamImplicit
import com.thoughtworks.xstream.annotations.XStreamOmitField
import com.thoughtworks.xstream.converters.collections.ArrayConverter
import com.thoughtworks.xstream.converters.extended.ToStringConverter
import sc.api.plugins.IBoard
import sc.plugin2020.util.Constants
import sc.plugin2020.util.CubeCoordinates
import sc.shared.PlayerColor
import kotlin.math.max
import kotlin.math.min

@XStreamAlias(value = "board")
class Board: IBoard {
    @XStreamOmitField
    private val shift = (Constants.BOARD_SIZE - 1) / 2

    // NOTE that this adds <null/> to the XML where fields of the array are null. This is required for proper deserialization, maybe we find a better way
    @XStreamConverter(value = ArrayConverter::class, nulls = [ToStringConverter::class])
    @XStreamImplicit(itemFieldName = "fields")
    val gameField = Array<Array<Field?>>(Constants.BOARD_SIZE) { arrayOfNulls(Constants.BOARD_SIZE) }
    val fields: Collection<Field>
        get() = gameField.flatMap { it.filterNotNull() }

    constructor() {
        fillBoard()
    }

    constructor(fields: Collection<Field>) {
        var x: Int
        var y: Int
        for(f in fields) {
            if(f.coordinates.x > shift || f.coordinates.x < -shift || f.coordinates.y > shift || f.coordinates.y < -shift)
                throw IndexOutOfBoundsException()
            x = f.coordinates.x + shift
            y = f.coordinates.y + shift
            gameField[x][y] = f
        }
        fillBoard()
    }

    private fun fillBoard() {
        for(x in -shift..shift) {
            for(y in max(-shift, -x - shift)..min(shift, -x + shift)) {
                if(gameField[x + shift][y + shift] == null) {
                    gameField[x + shift][y + shift] = Field(CubeCoordinates(x, y))
                }
            }
        }
    }

    fun getField(pos: CubeCoordinates): Field {
        return gameField[pos.x + shift][pos.y + shift] ?: throw IndexOutOfBoundsException()
    }

    override fun getField(cubeX: Int, cubeY: Int): Field {
        return this.getField(CubeCoordinates(cubeX, cubeY))
    }

    fun getField(cubeX: Int, cubeY: Int, cubeZ: Int): Field {
        return this.getField(CubeCoordinates(cubeX, cubeY, cubeZ))
    }

    /**
     * Only for tests!
     * use inversion of GameState.allPieces
     */
    fun getPieces(): List<Piece> {
        val pieces = mutableListOf<Piece>()
        for(x in -shift..shift) {
            for(y in max(-shift, -x - shift)..min(shift, -x + shift)) {
                val field = gameField[x + shift][y + shift]
                if(field != null)
                    pieces.addAll(field.pieces)
            }
        }
        return pieces
    }

    /**
     * Only for tests!
     * Use inversion of GameState.undeployed{Red,Blue}Pieces
     */
    fun getPiecesFor(color: PlayerColor): List<Piece> {
        return this.getPieces().filter { it.owner == color }
    }

    override fun toString(): String {
        var text = "Board\n"
        for(x in 0 until Constants.BOARD_SIZE) {
            for(y in 0 until Constants.BOARD_SIZE) {
                text = text + if(this.gameField[x][y] == null) "0" else "x"
            }
            text = text + "\n"
        }
        return text
    }
}