package org.example

import java.io.File

fun main() {
    val reader = File("/home/kondzitsu/Projects/Kotlin/adventOfCode/adventOfCode3/src/main/resources/input")
        .bufferedReader(Charsets.UTF_8)

    val disabledRegex = Regex("don't\\(\\).*?do\\(\\)")

    var fullFile = "do()"
    var line = reader.readLine()

    while (line != null) {
        fullFile += line
        line = reader.readLine()
    }

    fullFile += "do()"
    print(fullFile)
    val filteredFile = disabledRegex.replace(fullFile, "")


    val regex = Regex("(mul\\()(\\d{1,3},\\d{1,3})(\\))")
    val results = regex.findAll(filteredFile)
    var finalResult = 0
    for (result in results) {
        println(result.groupValues[2])
        var multiplicationResult = 0
        val split = result.groupValues[2].split(",")
        multiplicationResult = split[0].toInt() * split[1].toInt()
        finalResult += multiplicationResult
    }

    println(finalResult)



}