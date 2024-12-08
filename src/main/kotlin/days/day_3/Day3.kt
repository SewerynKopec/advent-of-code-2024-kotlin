package org.example.days.day_3

import org.example.days.DayTemplate
import sun.swing.plaf.synth.DefaultSynthStyle
import kotlin.collections.joinToString

class Day3: DayTemplate() {
    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_3/input-data.txt"

        private const val MUL_TEMPLATE = "mul\\(\\d{1,3},\\d{1,3}\\)"
        private const val NUMBER_TEMPLATE = "\\d{1,3}"
        private const val DO_TEMPLATE = "do\\(\\)"
        private const val DONT_TEMPLATE = "don't\\(\\)"

        private val MUL_REGEX = MUL_TEMPLATE.toRegex()
        private val NUMBER_REGEX = NUMBER_TEMPLATE.toRegex()
        private val DO_REGEX = DO_TEMPLATE.toRegex()
        private val DONT_REGEX = DONT_TEMPLATE.toRegex()
        private val ENABLING_MUL_REGEX = "($DO_TEMPLATE|$DONT_TEMPLATE|$MUL_TEMPLATE)".toRegex()
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        val matches = findMultiplication(content)
        val sum = extractMultiplySum(matches)
        println(sum)
    }

    override fun executePartTwo() {
        val content = getContent(FILE_PATH)
        val matches = findEnablingMultiplication(content)
        val filteredMatches = excludeMatches(matches)
        val sum = extractMultiplySum(filteredMatches)
        println(sum)
    }

    private fun matchRegex(regex: Regex, data: String): List<String> =
        regex.findAll(data).map { it.value }.toList()

    private fun findMultiplication(content: List<String>): List<String> =
        matchRegex(MUL_REGEX, content.toString())

    private fun extractNumbersToMultiply(matches: List<String>): List<Pair<Int,Int>> =
        matches.map { match ->
            matchRegex(NUMBER_REGEX, match).map { it.toInt() }. let { Pair(it[0], it[1]) }
        }

    private fun executeMultiplication(numberPairsToMultiply: List<Pair<Int,Int>>) =
        numberPairsToMultiply.map { pair -> pair.first * pair.second }

    private fun getSum(input: List<Int>) = input.sum()

    private fun findEnablingMultiplication(content: List<String>) =
        matchRegex(ENABLING_MUL_REGEX, content.toString())

    private fun excludeMatches(matches: List<String>): List<String> {
        var isEnabled = true
        val filteredMatches = mutableListOf<String>()
        matches.forEach{ match ->
            if (DO_REGEX.matches(match)) {
                isEnabled = true
            }
            if (DONT_REGEX.matches(match))
                isEnabled = false
            if (MUL_REGEX.matches(match) && isEnabled)
                filteredMatches.add(match)
        }
        return filteredMatches.toList()
    }

    private fun extractMultiplySum(matches: List<String>): Int {
        val numberPairsToMultiply = extractNumbersToMultiply(matches)
        val multiplicationResult = executeMultiplication(numberPairsToMultiply)
        val sum = getSum(multiplicationResult)
        return sum
    }


}