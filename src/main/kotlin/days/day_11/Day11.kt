package org.example.days.day_11

import org.example.days.DayTemplate
import kotlin.math.pow

class Day11: DayTemplate() {

    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_11/input-data.txt"
        private const val NUMBER_OF_BLINKS = 75
        private const val MULTIPLICATOR = 2024L
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        val stones = getStones(content)
        println(stones)
        val transformedStones = blink(stones, NUMBER_OF_BLINKS)
        val numberOfStones = transformedStones.size
        println(numberOfStones)
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }

    private fun getStones(content: List<String>) =
        content.first().split(" ").map { it.toLong() }

    private fun blink(initialStones: List<Long>, numberOfBlinks: Int): List<Long> {
        if (numberOfBlinks == 0) return initialStones
        val transformedStones = mutableListOf<Long>()
        initialStones.forEach { stone ->
            if (stone == 0L) {
                transformedStones.add(1)
                return@forEach
            }
            getNumberOfDigits(stone).also { numberOfDigits ->
                if (numberOfDigits % 2 == 0) {
                    val power = 10.0.pow(numberOfDigits / 2).toLong()
                    transformedStones.add(stone / power)
                    transformedStones.add(stone % power)
                    return@forEach
                }
            }
            transformedStones.add(stone * MULTIPLICATOR)
        }
//        println(transformedStones)
        println("Number of blinks: ${NUMBER_OF_BLINKS - numberOfBlinks + 1}")
        println("Size: ${transformedStones.size}")
        return blink(transformedStones, numberOfBlinks - 1)
    }

    private fun getNumberOfDigits(number: Long): Int {
        var loop = 0
        var transformedNumber = number
        while (transformedNumber >= 1) {
            loop++
            transformedNumber /= 10
        }
        return loop
    }
}