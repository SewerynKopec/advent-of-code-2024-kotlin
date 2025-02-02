package org.example.days.day_12

import org.example.days.DayTemplate
import kotlin.collections.forEach

class Day12: DayTemplate() {

    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_12/input-data.txt"
        private var DIRECTION = 0
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        val contentAsArray = getContentAsArray(content)
        val (plantsFenceLengthList, plantsSurfaceList) = getPlantFencesAndSurfaces(contentAsArray)
        val price = getPrice(plantsSurfaceList, plantsFenceLengthList)
        print(price)
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }

    private fun getContentAsArray(content: List<String>): Array<CharArray> =
        content.map { row -> row.toCharArray() }.toTypedArray()

//    private fun getPlantCount(array: Array<CharArray>): List<Pair<Char, Int>> {
//        val plantsCountMap = mutableListOf<Pair<Char, Int>>()
//
//        array.forEach { row ->
//            row.forEach { cell ->
//                val count = plantsCountMap[cell] ?: 0
//                plantsCountMap[cell] = count + 1
//            }
//        }
//        return plantsCountMap
//    }

    private fun getPlantFencesAndSurfaces(array: Array<CharArray>): Pair<List<Pair<Char,Int>>, List<Pair<Char,Int>>>{
        val plantsSurfaceList = mutableListOf<Pair<Char, Int>>()
        val plantsFenceLengthList = mutableListOf<Pair<Char, Int>>()
        val visitMap = array.map { row -> row.map { false }.toTypedArray() }.toTypedArray()

        array.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                if (visitMap[rowIndex][colIndex] == true)
                    return@forEachIndexed
                val steps = walkAround(array, rowIndex, colIndex)
                plantsFenceLengthList += Pair(cell, steps)
                plantsSurfaceList += Pair(cell, updateVisitMap(visitMap, array, rowIndex, colIndex, array[rowIndex][colIndex]))
//                visitMap.forEach {
//                    it.forEach { if (it == true) print("T") else print("F") }
//                    println()
//                }
//                println()
//                val moveCases = listOf<Triple<Int, Int, Boolean>>(
//                    Triple(rowIndex - 1, colIndex, rowIndex > 0),
//                    Triple(rowIndex, colIndex + 1, colIndex < array[0].size - 1),
//                    Triple(rowIndex + 1, colIndex, rowIndex < array.size - 1),
//                    Triple(rowIndex, colIndex - 1, colIndex > 0),
//                )
//                moveCases.forEach { cases ->
//                    val (nextRow, nextCol, inBounds) = cases
//                    if (!inBounds) {
//                        plantsFenceLengthMap[cell] = (plantsFenceLengthMap[cell] ?: 0) + 1
//                        return@forEach
//                    }
//                    if (array[nextRow][nextCol] != cell)
//                        plantsFenceLengthMap[cell] = (plantsFenceLengthMap[cell] ?: 0) + 1
//                }
            }
        }
        return Pair(plantsFenceLengthList, plantsSurfaceList)
    }

    private fun walkAround(array: Array<CharArray>, rowIndex: Int, colIndex: Int): Int {
        val plant = array[rowIndex][colIndex]
        var currentRow = rowIndex
        var currentCol = colIndex - 1
        var isFirstStep = true
        var fenceLength = 0
        while (isFirstStep || (currentRow != rowIndex || currentCol != colIndex - 1 )) {
//            println("($currentRow) : ($currentCol)")
//            printPosition(array, currentRow, currentCol)
            fenceLength++
            val (nextRow, nextCol) = nextStep(currentRow, currentCol)
            if (inBounds(array, nextRow, nextCol) && array[nextRow][nextCol] == plant) {
                turnLeft()
                continue;
            }
            if(!isPlantOnRight(plant, array, nextRow, nextCol)) {
                turnRight()
                fenceLength--
            }
            isFirstStep = false
            currentRow = nextRow
            currentCol = nextCol
        }
        return fenceLength
    }

    private fun printPosition(array: Array<CharArray>, row: Int, col: Int) {
        for (i in -1..array.size) {
            for (j in -1..array[0].size) {
                if (i == row && j == col)
                    print("@")
                else if (!inBounds(array, i, j))
                    print(".")
                else print(array[i][j])
            }
            println()
        }
        println()
    }

    private fun inBounds(array: Array<CharArray>, rowIndex: Int, colIndex: Int): Boolean {
        if (rowIndex !in 0 until array.size)
            return false
        if (colIndex !in 0 until array[0].size)
            return false
        return true
    }

    private fun turnRight() {
        DIRECTION = (DIRECTION + 1).mod(4)
    }

    private fun turnLeft() {
        DIRECTION = (DIRECTION - 1).mod(4)
    }

    private fun isPlantOnRight(plant: Char, array: Array<CharArray>, rowIndex: Int, colIndex: Int): Boolean{
        turnRight()
        val (nextRow, nextCol) = nextStep(rowIndex, colIndex)
        if (nextRow !in 0 until array.size || nextCol !in 0 until array[0].size)
            return false.also { turnLeft() }
        return (array[nextRow][nextCol] == plant).also { turnLeft() }
    }

    private fun nextStep(currentRow: Int, currentCol: Int): Pair<Int , Int> {
        val moveCases = listOf<Pair<Int, Int>>(
            Pair(currentRow - 1, currentCol),
            Pair(currentRow, currentCol + 1),
            Pair(currentRow + 1, currentCol),
            Pair(currentRow, currentCol - 1),
        )
        return try {
            moveCases[DIRECTION]
        } catch (ex: IndexOutOfBoundsException) {
            println(DIRECTION)
            throw ex
        }
    }

    private fun getPrice(surfaces: List<Pair<Char,Int>>, fenceLength: List<Pair<Char, Int>>): Int {
        var sum = 0
        for (i in 0 until surfaces.size) {
            sum += surfaces[i].second * fenceLength[i].second
            println("Plant: ${surfaces[i].first}")
            println("Surface: ${surfaces[i].second}")
            println("Fence length: ${fenceLength[i].second}")
            println()
        }
        return sum
    }

    private fun updateVisitMap(visitMap: Array<Array<Boolean>>, array: Array<CharArray>, row: Int, col: Int, plant: Char): Int {
        visitMap[row][col] = true
        listOf(Pair(row + 1, col), Pair(row, col + 1)).map { (nextRow, nextCol) ->
            println("($nextRow) : ($nextCol)")
            if (!inBounds(array, nextRow, nextCol))
                return@map 0
            if (array[nextRow][nextCol] != plant)
                return@map 0
            if (visitMap[nextRow][nextCol] == true)
                return@map 0
            return@map updateVisitMap(visitMap, array, nextRow, nextCol, plant)
        }. let {
            return 1 + it.sum()
        }
    }
}