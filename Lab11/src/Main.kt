import java.util.regex.Pattern

fun main() {
    val board = ChessBoard()
    board.CorrectCountOfFigure(PawnFigure(SideColor.White))
    board.CorrectCountOfFigure(PawnFigure(SideColor.Black))

    var cmd = ""
    var win = 0
    while (win == 0) {
        board.PrintBoard()

        print("Choose figure: ")
        val coords_1 = CoordsEnter()

        if (coords_1 != null) {
            board.PrintBoardWithSelection(coords_1.first, coords_1.second)
        } else {
            println("Incorrect enter!!!")
            readLine()
            continue
        }

        val figure = board.GetFigure(coords_1.first, coords_1.second)
        if (figure != null){
            print("Choose turn from (${figure.x}${figure.y}) to: ")
        } else continue

        val coords_2 = CoordsEnter()
        if (coords_2 != null) {
            board.MakeTurn(coords_1.first, coords_1.second, coords_2.first, coords_2.second)
        } else {
            println("Incorrect enter!!!")
            readLine()
            continue
        }

        //Runtime.getRuntime().exec("cls");
    }
}


fun CoordsEnter(): Pair<Char, Int>? {
    val textPattern = "^(\\w)(\\d)$"
    val pattern = Pattern.compile(textPattern)
    val cmd = readLine().toString()
    val matcher = pattern.matcher(cmd)
    if (matcher.matches()) {
        val x = matcher.group(1).toString()[0]
        val y = matcher.group(2).toString().toInt()
        return Pair(x, y)
    }
    else return null
}

