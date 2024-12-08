package org.example.days.day_2

import org.example.days.DayTemplate
import kotlin.math.absoluteValue

class Day2: DayTemplate() {

    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_2/input-data.txt"
        private const val SEPARATOR = " "
        private const val MIN_DIFFERENCE = 1
        private const val MAX_DIFFERENCE = 3
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        val count = countSafeReactors(content)
        println(count)
    }

    override fun executePartTwo() {
        val content = getContent(FILE_PATH)
        val count = countSafeReactorsWithDampener(content)
        println(count)
    }

    private fun countSafeReactorsWithDampener(content: List<String>): Int {
        var safeReactorsCount = 0
        content.forEach { reactorLevels ->
            val isSafe = parseReactorLevels(reactorLevels).run { checkSingleReactor(this, 1) }
            if (isSafe)
                safeReactorsCount++
        }
        return safeReactorsCount
    }

    private fun countSafeReactors(content: List<String>): Int {
        var safeReactorsCount = 0
        content.forEach { reactorLevels ->
           if(parseReactorLevels(reactorLevels).run { checkSingleReactor(this) })
               safeReactorsCount++
        }
        return safeReactorsCount
    }

    private fun parseReactorLevels(reactorLevels: String) =
        reactorLevels.split(SEPARATOR).map { level -> level.toInt() }

    private fun checkSingleReactor(levelList: List<Int>, allowedFails: Int = 0): Boolean {
        var sign: Int = 0
        for (i in 0 until levelList.size - 1) {
            val diff = levelList[i] - levelList[i+1]

            if (i == 0)
                sign = diff
            if (sign * diff < 0)
                return checkSignFail(i, levelList, allowedFails)
            if (!isInRange(diff.absoluteValue)){
                return checkDiffFail(i, levelList, allowedFails)
            }
        }
        return true
    }

    private fun checkSignFail(index: Int, levelList: List<Int>, allowedFails: Int): Boolean {
        if (allowedFails == 0)
            return false

        if (index + 1 == levelList.size - 1)
            return true
        if (index == 1 && isSafeWithoutNthLevel(levelList, 0, allowedFails))
            return true
        if(isSafeWithoutNthLevel(levelList, index + 1, allowedFails))
            return true
        if(isSafeWithoutNthLevel(levelList, index, allowedFails))
            return true
        return false
    }

    private fun checkDiffFail(index: Int, levelList: List<Int>, allowedFails: Int): Boolean {
        if (allowedFails == 0)
            return false
        if (index + 1 == levelList.size - 1)
            return true
        if(isSafeWithoutNthLevel(levelList, index + 1, allowedFails))
            return true
        if(isSafeWithoutNthLevel(levelList, index, allowedFails))
            return true
        return false
    }

    private fun isSafeWithoutNthLevel(levelList: List<Int>, n: Int, allowedFails: Int ): Boolean {
        val listWithoutNthLevel = levelList.toMutableList()
        listWithoutNthLevel.removeAt(n)
        return checkSingleReactor(listWithoutNthLevel.toList(), allowedFails - 1).also {
            if(it == false ) return@also
            println(levelList)
            println(listWithoutNthLevel)
            println()
        }
    }

    private fun isInRange(difference: Int) = difference >= MIN_DIFFERENCE && difference <= MAX_DIFFERENCE
}