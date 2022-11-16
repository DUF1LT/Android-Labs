import java.lang.Exception
import kotlin.reflect.typeOf

public class ChessBoard {

    private var board: Array<Array<ChessCell>> = Array(16) { Array(16) { ChessCell() } }

    constructor() {
        for (i in 0..7){
            for (j in 0..7) {
                if (j == 1)
                    board[i][j] = ChessCell('A' + i, j + 1, PawnFigure('A' + i, j + 1, SideColor.White))
                else if (j == 6)
                    board[i][j] = ChessCell('A' + i, j + 1, PawnFigure('A' + i, j + 1, SideColor.Black))
                else
                    board[i][j] = ChessCell('A' + i, j + 1)
            }
        }
        //board[3][3].PlaceFigure(PawnFigure('A' + 3, 1 + 3))
    }

    fun CorrectCountOfFigure(figure: IChessFigure): Boolean {
        var counter = 0
        for (i in 0..7) {
            for (j in 0..7) {
                var figureInBoard = GetFigure(i, j)
                if (figureInBoard != null && figureInBoard is PawnFigure) {
                    if (figureInBoard.sideColor == figure.sideColor)
                        counter++
                }
            }
        }
        if (figure is PawnFigure && counter > 8) {
            println("Too much Pawn figures of ${figure.sideColor}! ($counter/8)")
            return false
        }
        return true
    }

    fun GetCell(x: Char, y: Int): ChessCell {
        return board[x - 'A'][y - 1]
    }

    fun GetCell(x: Int, y: Int): ChessCell {
        return board[x][y]
    }

    fun HasFigure(x: Char, y: Int): Boolean {
        return board[x - 'A'][y - 1].figure != null
    }

    fun GetFigure(x: Char, y: Int): IChessFigure? {
        return board[x - 'A'][y - 1].figure
    }

    fun GetFigure(x: Int, y: Int): IChessFigure? {
        return board[x][y].figure
    }

    fun MakeTurn(x_from: Char, y_from: Int, x_to: Char, y_to: Int ) {
        val from_cell = GetCell(x_from, y_from)
        if (from_cell.figure == null) return
        else {
            val to_cell = GetCell(x_to, y_to)
            if (from_cell.figure!!.CanMoveTo(to_cell))
                from_cell.TurnTo(to_cell)
        }
    }

    fun PrintBoard() {
        for (j in 8 downTo -2){
            for (i in -2..8) {
                if (j == -2 && i >= 0 && i < 8)
                    print('A' + i)
                else if (i == -2 && j >= 0 && j < 8)
                    print(j + 1)
                else if (i == -1 || i == 8 || j == -1 || j == 8)
                    print('.')
                else if (i < 0 || i >= 8 || j < 0 || j >= 8)
                    print(' ')
                else
                    print(board[i][j].GetCellImage())
            }
            println()
        }
        println()
    }

    fun PrintBoardWithSelection(x: Char, y: Int) {
        var x_p = x - 'A'
        var y_p = y - 1
        var selectedFigure = GetFigure(x, y)
        for (j in 8 downTo -2){
            for (i in -2..8) {
                if (j == -2 && i >= 0 && i < 8)
                    print('A' + i)
                else if (i == -2 && j >= 0 && j < 8)
                    print(j + 1)
                else if (i == -1 || i == 8 || j == -1 || j == 8)
                    print('.')
                else if (i < 0 || i >= 8 || j < 0 || j >= 8)
                    print(' ')
                else {
                    if (selectedFigure != null) {
                        if (selectedFigure.CanMoveTo(board[i][j])) {
                            print('✖')
                            continue
                        }
                    }
                    print(board[i][j].GetCellImage())
                }
            }
            println()
        }
        println()
    }
}