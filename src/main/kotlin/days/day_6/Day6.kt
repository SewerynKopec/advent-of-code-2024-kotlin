package org.example.days.day_6

import org.example.days.DayTemplate

class Day6: DayTemplate() {

    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_6/input-data.txt"
        private val UP = '^'
        private val DOWN = 'V'
        private val LEFT = '<'
        private val RIGHT = '>'
        private val GUARD_DIRECTION_INDICATORS = listOf<Char>(UP, RIGHT, DOWN, LEFT)
        private const val OBSTACLE_INDICATOR = '#'
        private const val PATH_INDICATOR = 'X'
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        val steps = countSteps(content)
        println(steps)
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }

    private fun countSteps(content: List<String>): Int {
        val contentAsArray = content.map { it.toCharArray() }.toTypedArray()
        val (initialRow, initialCol) = findInitialGuardPosition(contentAsArray)
        val steps = findAndCountGuardPath(initialRow, initialCol, contentAsArray)

        return steps
    }

    private fun findInitialGuardPosition(content: Array<CharArray>): Pair<Int,Int> {
        content.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, col ->
                if (col in GUARD_DIRECTION_INDICATORS)
                    return Pair(rowIndex, colIndex)
            }
        }
        return Pair(-1,-1)
    }

    private fun findAndCountGuardPath(initialRow: Int, initialCol: Int, content: Array<CharArray>): Int {
        var direction = GUARD_DIRECTION_INDICATORS.indexOf(content[initialRow][initialCol])
        var steps = 0
        var (row, col) = Pair(initialRow, initialCol)
        do {
            if (content[row][col] != PATH_INDICATOR){
                steps++
                content[row][col] = PATH_INDICATOR
            }
            getNextCoordinates(direction, row, col, content).apply {
                direction = this.first
                row = this.second
                col = this.third
            }
        } while (row != -1 || col != -1)

        return steps
    }

    private fun getNextCoordinates(direction: Int, row: Int, col: Int, content: Array<CharArray>): Triple<Int, Int, Int> {
        val (nextRow, nextCol) = when(GUARD_DIRECTION_INDICATORS[direction]) {
            UP ->
                Pair(row - 1 , col)
            RIGHT ->
                Pair(row, col + 1)
            DOWN ->
                Pair(row + 1, col)
            LEFT ->
                Pair(row, col - 1)
            else ->
                Pair(-1, -1)
        }

        if (nextRow !in 0 until content.size || nextCol !in 0 until content[0].size)
            return Triple(-1, -1, -1)

        if (content[nextRow][nextCol] == OBSTACLE_INDICATOR) {
            val nextDirection = (direction + 1) % 4
            return getNextCoordinates(nextDirection, row, col, content)
        }

        return Triple(direction, nextRow, nextCol)
    }
}