package LAB_10.Task3
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.math.pow


fun main(args: Array<String>) {
    aTask()
    println("----------------")
    bTask()
    println("----------------")
    cTask()
    println("----------------")
    dTask()
    println("----------------")
    eTask()
}

fun aTask() {
    print("Enter login: ")
    var login: String? = readLine()
    print("Enter password: ")
    var password: String? = readLine()

    if (isValid(login, password))
        println("Account registered!")
    else
        println("Incorrect login or password!")
}

fun bTask() {
    val patternText = "^(\\d{2})[.|/| |-](\\d{2})[.|/| |-](\\d{4})$"
    val pattern = Pattern.compile(patternText)
    print("Enter date: ")
    val dateFormat = readLine()

    if (dateFormat == null) {
        println("Date null value")
        return
    }
    val matcher = pattern.matcher(dateFormat)
    if (!matcher.matches()) {
        println("Invalid date format")
        return
    }

    val day = matcher.group(1).toInt()
    val month = matcher.group(2).toInt() - 1
    val year = matcher.group(3).toInt()
    val date = Date(year, month, day)
    println("Selected date: $date")

    println("Day: ${checkDate(date)}")
}

fun cTask() {
    print("A value: ")
    val a = readLine()?.toInt()
    print("Operation: ")
    val operation = readLine().toString()[0]
    print("B value: ")
    val b = readLine()?.toInt()

    if (a != null && b != null) {
        println("= ${doOperation(a, b, operation)}")
    };
}

fun dTask() {
    val patternText = "[-?0-9]+"
    var pattern = Pattern.compile(patternText)
    print("Enter array: ")
    val arrayText = readLine()
    val matcher = pattern.matcher(arrayText)
    val array = ArrayList<Int>()
    while (matcher.find()) {
        val n = matcher.group().toInt()
        array.add(n)
    }
    println("Index of max: ${indexOfMax(array.toIntArray())}")
}

fun eTask() {
    print("Enter  first string: ")
    val first = readLine().toString()
    print("Enter second string: ")
    val second = readLine().toString()
    println("Coincidence: ${first.coincidence(second)} symbols")
}

//-------------------------------------------------------

fun isValid(login: String?, password: String?): Boolean {

    fun notNull(text: String?) = !text.isNullOrEmpty()

    val emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    if (notNull(login) || notNull(password)) {
        val nnLogin: String = login.toString()
        val nnPassword: String = password.toString()

        val pattern = Pattern.compile(emailPattern)

        if (!pattern.matcher(nnLogin).matches()) return false
        if (nnPassword.length !in 6..12) return false
        if (nnPassword.contains(' ')) return false

        return true
    }
    return false
}

class YearDate(day: Int, month: Int) {
    var Day: Int = day
    var Month: Int = month

    override fun toString(): String {
        return "$Day day of $Month month"
    }
}

enum class Holiday(val yearDate: YearDate, val holiday: String) {
    NewYear(YearDate(1,0), "New year holiday"),
    OrthodoxChristmas(YearDate(7,0), "Orthodox christmas holiday"),
    ClassicChristmas(YearDate(25,11), "Classic christmas holiday");

    fun adoptDate(date: Date): Date {
        return Date(date.year, yearDate.Month, yearDate.Day)
    }
}

fun checkDate(date: Date): String {

    fun isWeekend(date: Date?): Boolean {
        if (date == null) return false
        val c = Calendar.getInstance()
        c.time = date
        val dayOfWeek = c[Calendar.DAY_OF_WEEK]
        return dayOfWeek == 1 || dayOfWeek == 7
    }

    when (date) {
        Holiday.NewYear.adoptDate(date) -> return Holiday.NewYear.holiday
        Holiday.OrthodoxChristmas.adoptDate(date) -> return Holiday.OrthodoxChristmas.holiday
        Holiday.ClassicChristmas.adoptDate(date) -> return Holiday.ClassicChristmas.holiday
    }
    return if (isWeekend(date)) "Weekend" else "Weekday"
}

fun doOperation(a: Int, b: Int, operation: Char): Double {
    return when (operation) {
        '+' -> a.toDouble() + b.toDouble()
        '-' -> a.toDouble() - b.toDouble()
        '*' -> a.toDouble() * b.toDouble()
        '/' -> a.toDouble() / b.toDouble()
        '%' -> a.toDouble() % b.toDouble()
        '^' -> a.toDouble().pow(b)
        else -> throw Exception("Invalid operation")
    }
}

fun indexOfMax(a: IntArray): Int? {
    if (a.isEmpty())
        return null
    var index: Int = 0
    for (i in a.indices) {
        if (a[i] > a[index])
            index = i
    }
    return index
}

//fun IntArray.indexOfMax(): Int? {
//    if (this.isEmpty())
//        return null
//    var index: Int = 0
//    for (i in this.indices) {
//        if (this[i] > this[index])
//            index = i
//    }
//    return index
//}

fun String.coincidence(another: String): Int {
    var counter = 0
    val maxLen: Int =
        if (this.length > another.length)
            another.length else this.length
    for (i in 0 until maxLen) {
        if (this[i] == another[i]) {
            counter++
        }
    }
    return counter
}