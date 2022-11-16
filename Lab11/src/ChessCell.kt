class ChessCell() {
    var x: Char = 'A'
        get private set
    var y: Int = 1
        get private set
    var figure: IChessFigure? = null
        get private set

    constructor(x: Char, y: Int, figure: IChessFigure? = null) : this() {
        this.x = x
        this.y = y
        this.figure = figure
    }

    fun PlaceFigure(figure: IChessFigure?): IChessFigure? {
        var last: IChessFigure? = this.figure
        this.figure = figure
        return last
    }

    fun TurnTo(cell: ChessCell): IChessFigure? {
        if (this != cell) {
            val bitted_figure = cell.PlaceFigure(figure)
            figure = null
            return bitted_figure
        }
        return null
    }

    fun GetCellImage(): Char {
        if (figure != null)
            return figure!!.GetFigureImage()
        return ' '
    }
}