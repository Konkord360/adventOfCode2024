package org.example

import java.io.File

fun main() {
    val reader = File("/home/kondzitsu/Projects/Kotlin/adventOfCode/adventOfCode4/src/main/resources/input")
        .bufferedReader(Charsets.UTF_8)
    var wholeFile: List<String> = reader.readLines()

    val fileArray = Array(wholeFile.size) { charArrayOf() }
    for (line in wholeFile.indices) {
        fileArray[line] = wholeFile[line].toCharArray()
    }
//    partOne(fileArray)
    partTwo(fileArray)
//    printTable(fileArray)
//    println("**************")
//    printTable(turnArraySideways(fileArray))
}

val testArray = arrayOf(
    "MSAMS", "SSAMM", "MMASS", "SMASM"
)

fun partTwo(fileArray: Array<CharArray>) {
    var containCount = 0
    for (i in 1..<fileArray.size - 1) {
        for (j in 1..<fileArray[i].size - 1) {
           var testString = ""
            testString += fileArray[i-1][j-1].toString()
            testString += fileArray[i-1][j+1].toString()
            testString += fileArray[i][j].toString()
            testString += fileArray[i+1][j-1].toString()
            testString += fileArray[i+1][j+1].toString()
            if (testArray.contains(testString)){
                containCount++
            }
        }
    }
    println(containCount)
}

fun partOne(fileArray: Array<CharArray>) {
    var total = 0
    total += countHorizontalOccurrences(fileArray)
    total += countHorizontalOccurrences(turnArray(fileArray))
    total += countDiagonalOccurrences(fileArray)
    total += countDiagonalOccurrences(turnArraySideways(fileArray))
    println(total)
}

fun turnArraySideways(fileArray: Array<CharArray>): Array<CharArray> {
    val fileArrayTurned = Array(fileArray.size) { charArrayOf() }
    for (i in fileArray.indices) {
        fileArrayTurned[i] = fileArray[i].toList().reversed().toCharArray()
    }
    return fileArrayTurned
}

fun turnArray(fileArray: Array<CharArray>): Array<CharArray> {
    val fileArrayTurned = Array(fileArray.size) { charArrayOf() }
    for (line in fileArray.indices) {
        fileArrayTurned[line] = fileArray[line].copyOf()
    }

    for (i in fileArray.indices) {
        for (j in fileArray[i].indices) {
            fileArrayTurned[i][j] = fileArray[j][i]
        }
    }
    return fileArrayTurned
}

fun countVerticalOccurrences(fileArray: Array<CharArray>): Int {
    var columnString = ""
    var turnedArray = turnArray(fileArray)
    var sum = 0
    return countHorizontalOccurrences(turnedArray)
}

fun countHorizontalOccurrences(fileArray: Array<CharArray>): Int {
    var sum = 0
    for (i in fileArray.indices) {
        sum += countOccurrences(fileArray[i].concatToString())
    }
    return sum
}

fun countDiagonalOccurrences(fileArray: Array<CharArray>): Int {
    //[a][b][c][d]
    //[a][b][c][d]
    //[a][b][c][d]
    //[a][b][c][d]
    var columnString1 = ""
    var columnString2 = ""
    var sum = 0

    for (i in fileArray[0].indices) {
        if (i >= 3) {
            columnString1 += getDiagonalStringTop(fileArray, i)
        }
        if (i > 0 && i <= fileArray[0].size - 4) {
            columnString2 += getDiagonalStringBottom(fileArray, i)
        }
//        println(columnString1)
//        println("******************")
//        println(columnString2)
        sum += countOccurrences(columnString1) + countOccurrences(columnString2)
//        println(columnString2)
        columnString1 = ""
        columnString2 = ""
    }

    return sum
}

fun getDiagonalStringTop(fileArray: Array<CharArray>, i: Int): String {
    var diagonalStringTop = ""
    for (j in i downTo 0) {
        diagonalStringTop += fileArray[i - j][j]
    }
    return diagonalStringTop
}

fun getDiagonalStringBottom(fileArray: Array<CharArray>, i: Int): String {
    var diagonalStringBottom = ""
    for (j in 0..<fileArray[0].size - i) {
        diagonalStringBottom += fileArray[fileArray.size - j - 1][j + i]
    }
    return diagonalStringBottom
}

fun countOccurrences(input: String): Int {
    return input.windowed("XMAS".length) {
        if (it == "XMAS" || it == "SAMX") {
            1
        } else {
            0
        }
    }.sum()
}

fun printTable(lines: Array<CharArray>) {
    for (line in lines) {
        for (char in line) {
            print(char)
        }
        println()
    }
}