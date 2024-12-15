package org.example.days.day_10

import org.example.days.DayTemplate

class Day10: DayTemplate() {

    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_10/input-data.txt"
        private val BLANK = Pair(-1,-1)
        private const val LIMIT = 9
        private const val START = 0
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        val contentAsArray = getContentAsArray(content)
        contentAsArray.forEach {
            print(it.joinToString())
            println()
        }
        println()
        val pathArray = getPathArray(contentAsArray)
        val score = getScore(contentAsArray, pathArray)
        pathArray.forEach {
            print(it.joinToString())
            println()
        }
        println(score)
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }

    private fun getContentAsArray(content: List<String>) =
        content.map { row ->
            row.split("").drop(1).dropLast(1).map {
                it.toInt()
            }.toTypedArray()
        }.toTypedArray()

    private fun getPathArray(inputArray: Array<Array<Int>>): Array<Array<Int>> {
        var trailArray = prepareBlankArray(inputArray)
        inputArray.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                getTrailEnds(inputArray, trailArray, rowIndex, colIndex)
            }
        }
        return trailArray.map { row -> row.map { cell -> cell.size }.toTypedArray() }.toTypedArray()
    }

    private fun prepareBlankArray(inputArray: Array<Array<Int>>): Array<Array<MutableSet<Pair<Int, Int>>>> =
        inputArray.map { it.map { mutableSetOf<Pair<Int, Int>>(BLANK) }.toTypedArray() }.toTypedArray()

    private fun getTrailEnds(
        inputArray: Array<Array<Int>>,
        trailArray: Array<Array<MutableSet<Pair<Int, Int>>>>,
        initRow: Int,
        initCol: Int,
    ) {
        getTrailEndsRecursive(
            inputArray,
            trailArray,
            initRow,
            initCol,
        )
    }

    private fun getTrailEndsRecursive(
        inputArray: Array<Array<Int>>,
        trailArray: Array<Array<MutableSet<Pair<Int, Int>>>>,
        initRow: Int,
        initCol: Int,
    ): Set<Pair<Int,Int>> {
        if (BLANK !in trailArray[initRow][initCol])
            return trailArray[initRow][initCol]
        if (inputArray[initRow][initCol] == LIMIT) {
            trailArray[initRow][initCol].remove(BLANK)
            trailArray[initRow][initCol].add(Pair(initRow, initCol))
            return trailArray[initRow][initCol]
        }

        val moveCases = listOf<Triple<Int, Int, Boolean>>(
            Triple(initRow - 1, initCol, initRow > 0),
            Triple(initRow, initCol + 1, initCol < trailArray[0].size - 1),
            Triple(initRow + 1, initCol, initRow < trailArray.size - 1),
            Triple(initRow, initCol - 1, initCol > 0),
        )
        moveCases.forEach { case ->
            val (nextRow, nextCol , inBounds ) = case
            if (inBounds) {
                if (inputArray[initRow][initCol] + 1 == inputArray[nextRow][nextCol]) {
                    getTrailEndsRecursive(inputArray, trailArray, nextRow, nextCol).also { trailEnds ->
                        trailArray[initRow][initCol].addAll(trailEnds)
                    }
                }
            }
        }
        trailArray[initRow][initCol].remove(BLANK)
        return trailArray[initRow][initCol]
    }

    private fun getScore(inputArray: Array<Array<Int>>, pathArray: Array<Array<Int>>): Int {
        var sum = 0
        inputArray.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                if (cell == START)
                    sum += pathArray[rowIndex][colIndex]
            }
        }
        return sum
    }
}