package org.example

import kotlinx.coroutines.*
import java.io.File
import kotlin.collections.ArrayList

val rules = mutableMapOf<String, MutableList<String>>()
fun main() = runBlocking {
    val reader = File("/home/kondzitsu/Projects/Kotlin/adventOfCode/adventOfCode5/src/main/resources/input")
        .bufferedReader(Charsets.UTF_8)

    var line = reader.readLine()
    while (line != "") {
        println(line)
        val splitLine = line.split("|")
        if (!rules.contains(splitLine[0])) {
            rules.putIfAbsent(splitLine.first(), mutableListOf(splitLine.last()))
        } else {
            rules[splitLine.first()]?.add(splitLine.last())
        }
        line = reader.readLine()
    }
    println(rules)

    var update = reader.readLine()
    val goodUpdates: MutableList<List<String>> = ArrayList()
    val updates: MutableList<List<String>> = ArrayList()
    while (update != null) {
        val updateSplit = update.split(",")
        updates.add(updateSplit)
        val goodUpdate = getBadPages(updateSplit).isEmpty()
        if (goodUpdate) {
            goodUpdates.add(updateSplit)
        }

        update = reader.readLine()
    }

    println("GOOD UPDATES")
    println(goodUpdates)
    var middleValuesSum = 0
    for (goodUpdate in goodUpdates) {
        middleValuesSum += goodUpdate[goodUpdate.size / 2].toInt()

    }
    println("Middle values sum")
    println(middleValuesSum)

    println("All updates size: ${updates.size}")
    updates.removeAll(goodUpdates)
    println("Bricked update count: ${updates.size}")

    val fixedUpdates: MutableList<List<String>> = ArrayList()
    for (leftUpdate in updates) {
        val copyList = leftUpdate.toMutableList()
//        var badPages = getBadPages(copyList)
        while (getBadPages(copyList).isNotEmpty()) {
            val badPages = getBadPages(copyList).toMutableList()
            copyList.removeAll(badPages)

            for (j in 0..<badPages.size) {
                for (i in 0..copyList.size) {
                    copyList.add(i, badPages[j])
                    if (getBadPages(copyList).isEmpty()) {
                        println(copyList)
                        break
                    } else {
                        copyList.removeAt(i)
                    }
                }
            }
        }
        fixedUpdates.add(copyList.toList())
    }
    println("FIXED UPDATES")
    println(fixedUpdates)

    for (fupdate in fixedUpdates) {
        println(fupdate)
        if (getBadPages(fupdate).isNotEmpty()) {
            println("STILL BAD")
        }
    }

    var fixedMiddleValuesSum = 0
    for (niceUpdate in fixedUpdates) {
        fixedMiddleValuesSum += niceUpdate[niceUpdate.size / 2].toInt()

    }
    println("Fixed middle values sum")
    println(fixedMiddleValuesSum)
}

fun getBadPages(update: List<String>): MutableList<String> {
    val badIndexes = mutableListOf<String>()
    for (pageIndex in update.indices) {
        if (pageIndex > 0) {
            val notBeforePages = rules[update[pageIndex]]
            for (i in pageIndex - 1 downTo 0) {
                if (notBeforePages != null) {
                    if (notBeforePages.contains(update[i])) {
                        badIndexes.add(update[pageIndex])
                        break
                    } else {
                    }
                }
            }
        }
    }
    return badIndexes
}