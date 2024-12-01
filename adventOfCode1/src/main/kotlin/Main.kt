package org.example

import kotlin.math.abs


fun main() {
//    println(calculateListDifference())

    println(getSimilirarity())
//    println(difference)
}

fun calculateListDifference(): Long {
    var difference: Long = 0
    val sortedList1 = list.sorted()
    val sortedList2 = list2.sorted()
    for (i in sortedList1.indices) {
        difference += abs(sortedList1[i] - sortedList2[i])
    }
    return difference
}

fun getSimilirarity(): Long {
    var score: Long = 0
    val occurrenceMap = mutableMapOf<Int, Int>()
    for (i in list.indices) {
        println("Number ${list[i]}")
        val occurances = getOccurrences(list[i])
        println("occurances $occurances")
        occurrenceMap[list[i]] = occurances
        println("value ${occurrenceMap[list[i]]}")
    }

    occurrenceMap.forEach { (key, value) ->
        run {
            score += key * value

        }
    }

    return score
}

fun getOccurrences(number: Int): Int {
    var count = 0
    list2.forEach {
        if (it == number)
            count++
    }

    return count
}
