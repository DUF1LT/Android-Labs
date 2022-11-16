interface IChessFigure {
    var x: Char
        get
    var y: Int
        get
    var sideColor: SideColor
        get

    fun MoveTo(chessCell: ChessCell): IChessFigure?
    fun CanMoveTo(chessCell: ChessCell): Boolean
    fun GetFigureImage(): Char {
        return '*'
    }
}