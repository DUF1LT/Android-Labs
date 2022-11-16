package LAB_10.Task2

fun main(args: Array<String>) {
    //a)
    //val - const, var - let

    val valInt: Int
    valInt = 1243

    val valDouble: Double = 3.1415
    val valString = "Some text"

    var varInt = 9999
    var varDouble: Double
    var varString:String = "Some text 2"
    varDouble = 228.6
    // Val cannot be reassigned - valDouble = 22.0

    //b)
    println("$valDouble + $varDouble = ${valDouble + varDouble}")
    varInt = varDouble.toInt()
    println("$valInt")


    //c)
    var constVal: Int? = null
    constVal = 1312
    println(constVal)

    var readed = readLine()?.toBigDecimal()
    println(readed)
}
