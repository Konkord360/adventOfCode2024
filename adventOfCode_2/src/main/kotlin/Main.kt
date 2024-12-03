package org.example

import java.io.File
import kotlin.math.abs

fun main() {
    val reader = File("/home/kondzitsu/Projects/Kotlin/adventOfCode/adventOfCode_2/src/main/resources/inputFile")
        .bufferedReader(Charsets.UTF_8)

    //the code below came from a mind trying to understand kotlin and do it a Kotlin way. Tried to be smart at the first
    //part and it came out to be terrible for the second part. It can probably be cleaned up to become a little bit better
    //but in its form now it might be considered a sacrilege
    var safe = 0
    var reportLine = reader.readLine()
    while (reportLine != null) {
        if (isSafe(reportLine)) {
            println(reportLine)
            println("safe")
            safe++
        }
        else {
            println(reportLine)
            println("unsafe")
        }
        reportLine = reader.readLine()
    }
    println(safe)
}

fun isSafe(reportLine: String): Boolean {
    val originalReport = reportLine.split(" ").toMutableList()
    val report1 = reportLine.split(" ").toMutableList()
    val ascReport = report1.sortedBy { it.toInt() }
    if (isSafeFunc(report1, ascReport)) {
        return true
    } else {
        val index1 = report1.indexOfFirstDifferentElement(ascReport)
        val index2 = report1.indexOfLastDifferentElement(ascReport)
        val report11 = reportLine.split(" ").toMutableList()
        val report12 = reportLine.split(" ").toMutableList()
        if (index1 != -1) {
            report11.removeAt(index1)
            if (isSafeFunc(report11, report11.sortedBy { it.toInt() })) {
                return true
            }
        }
        if (index2 != -1) {
            report12.removeAt(index2)
            if (isSafeFunc(report12, report12.sortedBy { it.toInt() })) {
                return true
            }
        }
    }

    val report2 = reportLine.split(" ").toMutableList()
    val descReport = report2.sortedByDescending { it.toInt() }
    if (isSafeFunc(report2, descReport)) {
        return true
    } else {
        val index1 = report2.indexOfLastDifferentElement(descReport)
        val index2 = report2.indexOfFirstDifferentElement(descReport)
        val report21 = reportLine.split(" ").toMutableList()
        val report22 = reportLine.split(" ").toMutableList()
        if (index1 != -1) {
            report21.removeAt(index1)
            if (isSafeFunc(report21, report21.sortedByDescending { it.toInt() })) {
                return true
            }
        }
        if (index2 != -1) {
            report22.removeAt(index2)
            if (isSafeFunc(report22, report22.sortedByDescending { it.toInt() })) {
                return true
            }
        }
    }

    return false
}

fun isSafeFunc(report: List<String>, sortedReport: List<String>): Boolean {
    if (report == sortedReport) {
        if (maxThreeStep(report)) {
            return true
        }
    }
    return false
}

fun maxThreeStep(report: List<String>): Boolean {
    //adjust to count the number of failed counts nah its a little harder
    var prev = report.first().toInt() - 1
//    var failCount = 0
    for (number in report) {
        val absDiff = abs(number.toInt() - prev)

        if (absDiff == 0 || absDiff > 3) {
            return false
        }
        prev = number.toInt()
    }
    //0 6 2 4 | 0 5 2 4 | 0 5 2 4 9 7- one wrong element can fail up to two
    return true
}

fun maxThreeStepIndexFirst(report: List<String>): Int {
    var prev = report.first().toInt() - 1
    var i = 0
//    var failCount = 0
    for (number in report) {
        val absDiff = abs(number.toInt() - prev)

        if (absDiff == 0 || absDiff > 3) {
            return i - 1
        }
        i++
        prev = number.toInt()
    }
    //0 6 2 4 | 0 5 2 4 | 0 5 2 4 9 7- one wrong element can fail up to two
    return -1
}

fun maxThreeStepIndexLast(report: List<String>): Int {
    var prev = report.first().toInt() - 1
    var i = 0
//    var failCount = 0
    for (number in report) {
        val absDiff = abs(number.toInt() - prev)

        if (absDiff == 0 || absDiff > 3) {
            return i
        }
        i++
        prev = number.toInt()
    }
    //0 6 2 4 | 0 5 2 4 | 0 5 2 4 9 7- one wrong element can fail up to two
    return -1
}

fun MutableList<String>.indexOfFirstDifferentElement(list: List<String>): Int {
    for (i in this.indices) {
        if (this[i].toInt() != list[i].toInt()) {
            return i
        }
    }
    return maxThreeStepIndexFirst(list)
}

fun MutableList<String>.indexOfLastDifferentElement(list: List<String>): Int {
    for (i in this.indices) {
        if (this[this.size - 1 - i].toInt() != list[list.size - 1 - i].toInt()) {
            return this.size - 1 - i
        }
    }
    return maxThreeStepIndexLast(list)
}
