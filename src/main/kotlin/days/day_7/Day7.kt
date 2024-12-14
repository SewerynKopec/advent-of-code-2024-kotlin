package org.example.days.day_7

import org.example.days.DayTemplate

class Day7: DayTemplate() {

    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_7/input-data.txt"
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        val calibrationEquations = getCalibrationEquations(content)
        val count = sumValidEquations(calibrationEquations)
        println(count)
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }

    private fun getCalibrationEquations(content: List<String>): List<Pair<Long, List<Long>>> {
        val equations = mutableListOf<Pair<Long,List<Long>>>()
        content.forEach { row ->
            val result = row.split(": ").first().toLong()
            val numbers = row.split(": ").last().split(" ").map { it.toLong() }
            equations.add(Pair(result, numbers))
        }
        return equations
    }

    private fun sumValidEquations(equations: List<Pair<Long, List<Long>>>): Long {
        val validEquations = equations.filter { (result, numbers) ->
            numbers.sum().also { sum ->
                if (sum == result) return@filter true
                if (sum > result) return@filter false
            }
            return@filter isValid(result, numbers.first(), numbers.drop(1))
        }
        return validEquations.map { it.first }.sum()
    }

    private fun isValid(targetResult: Long, currentValue: Long, numbersLeft: List<Long>): Boolean {
        if (numbersLeft.isEmpty())
            return currentValue == targetResult

        var nextValue = currentValue * numbersLeft.first()
        if (nextValue > 0 && nextValue <= targetResult)
            if (isValid(targetResult, nextValue, numbersLeft.drop(1)))
                return true

        nextValue = currentValue + numbersLeft.first()
        if (nextValue <= targetResult)
            if (isValid(targetResult, nextValue, numbersLeft. drop(1)))
                return true
        return false
    }

}