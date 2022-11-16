package LAB_10.Task5

fun main(args: Array<String>) {
    aTask()
    bTask()
    cTask()
}

fun aTask() {
    fun biggerThan5(collection: Collection<Int>): Boolean = collection.size > 5
    fun oddContainsIn(collection: Collection<Int>): Boolean = collection.any { t -> t % 2 == 1 }
    val arr = arrayListOf(2, 4, 1, 8, 10, 12)
    println(">5: ${biggerThan5(arr)}")
    println("has odd: ${oddContainsIn(arr)}")
}

fun bTask() {
    val list = arrayListOf(2, 4, 1, 8, 10, 2, 12, 10)
    printArray(list.toIntArray())
    println("-------------------")
    list.add(1)
    printArray(list.toIntArray())
    println("-------------------")
    list += 23
    printArray(list.toIntArray())
    println("-------------------")
    val newList = list.distinct()
    printArray(newList.toIntArray())
    println("-------------------")
    newList.toMutableList().removeIf{it % 2 == 0}
    newList.forEach{t -> print("$t ")}
    println()
    println("-------------------")
    val numbers = listOf(1, 2, 3, 4, 5)
    println(numbers.filter(::isPrime))
    println("-------------------")
    val (first, second) = numbers
    println("$first $second")
}

fun cTask() {
    val scoreMap = mutableMapOf(
        40 to 10,
        39 to 9,
        38 to 8,
    )
    for (i in 0..18) scoreMap[i] = 1
    for (i in 19..21) scoreMap[i] = 2
    for (i in 22..24) scoreMap[i] = 3
    for (i in 25..28) scoreMap[i] = 4
    for (i in 29..31) scoreMap[i] = 5
    for (i in 32..34) scoreMap[i] = 6
    for (i in 35..37) scoreMap[i] = 7


    val answerCounts = listOf(3, 6, 10, 15, 20 , 31, 34, 35, 40)
    answerCounts.map { scoreMap[it] }.forEach{ print("$it ") }
    println()
    answerCounts.map { scoreMap[it] }.groupingBy { it }.eachCount().forEach {print("$it ")}
    println()
    answerCounts.count { (scoreMap[it] ?: 0) < 4 }.let { println("<4 count: $it") }
}

fun printArray(a: IntArray) {
    for (i in a.indices)
        print("${a[i]} ")
    println()
}

fun isPrime(value: Int): Boolean {
    val range = IntArray(value - 1)
    for (i in range.indices) {
        range[i] = i + 2
    }
    val simple = BooleanArray(value - 1)
    simple.fill(true)
    for (i in range.indices) {
        if (i >= value / 2) break
        if (!simple[i]) continue
        for (j in (i + 1)..range.lastIndex) {
            if (!simple[j]) continue
            if (range[j] % range[i] == 0)
                simple[j] = false
        }
    }
    return if (simple.isEmpty()) true
    else
        simple[simple.lastIndex]
}