package org.example.days.day_8

import jdk.jfr.ContentType
import org.example.days.DayTemplate
import sun.swing.plaf.synth.DefaultSynthStyle

class Day8: DayTemplate() {

    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_8/input-data.txt"
        private val ANTENNA_SYMBOL_REGEX = "([0-9]|[a-z]|[A-Z])".toRegex()
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        val contentArray = getContentAsArray(content)
        printArray(contentArray)
        val mapOfAntennas = getAntennaLocations(contentArray)
        val borders = Pair(contentArray.size, contentArray[0].size)
        val antinodeLocations = getAntinodeLocations(mapOfAntennas, borders)
        println(antinodeLocations.size)
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }

    private fun getContentAsArray(content: List<String>) =
        content.map { it.toCharArray() }.toTypedArray()

    private fun getAntennaLocations(array: Array<CharArray>): Map<Char, List<Pair<Int, Int>>> {
        val mapOfAntennas = mutableMapOf<Char, List<Pair<Int,Int>>>()
        array.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                if(ANTENNA_SYMBOL_REGEX.matches(cell.toString()))
                    mapOfAntennas[cell] = mapOfAntennas[cell].orEmpty() + Pair(rowIndex, colIndex)
            }
        }
        return mapOfAntennas
    }

    private fun getAntinodeLocations(mapOfAntennas: Map<Char, List<Pair<Int, Int>>>, borders: Pair<Int, Int>): Set<Pair<Int,Int>> {
        val antinodeLocations = mutableSetOf<Pair<Int, Int>>()
        mapOfAntennas.forEach { _, singleTypeAntennas ->
            getAntinodeLocationsForSingleTypeAntennas(singleTypeAntennas, borders).also { locations ->
                antinodeLocations.addAll(locations)
                printAnntenasWithTheirAntinodes(singleTypeAntennas.toSet(), locations)
            }
        }
        return antinodeLocations
    }

    private fun getAntinodeLocationsForSingleTypeAntennas(
        antennas: List<Pair<Int, Int>>,
        borders: Pair<Int, Int>,
        antinodes: MutableSet<Pair<Int, Int>> = mutableSetOf<Pair<Int, Int>>()
    ): Set<Pair<Int, Int>> {
        val currentAntenna = antennas.first()
        val otherAntennas = antennas.drop(1)
        if (otherAntennas.isEmpty())
            return antinodes
        otherAntennas.forEach { otherAntenna ->
            calculateAntinodeLocations(currentAntenna, otherAntenna).also { newAntiNodes ->
                newAntiNodes.forEach { newAntiNode ->
                    if (checkIfInBorders(newAntiNode, borders))
                        antinodes.add(newAntiNode)
                }
            }
        }
        return getAntinodeLocationsForSingleTypeAntennas(antennas.drop(1), borders, antinodes)
    }

    private fun checkIfInBorders(location: Pair<Int, Int>, borders: Pair<Int, Int>): Boolean {
        if (location.first < 0)
            return false
        if (location.second < 0)
            return false
        if (location.first >= borders.first)
            return false
        if (location.second >= borders.second)
            return false
        return true
    }

    private fun calculateAntinodeLocations(pair1: Pair<Int,Int>, pair2: Pair<Int, Int>) =
        setOf(
            Pair(
                2*pair2.first - pair1.first,
                2*pair2.second - pair1.second
            ),
            Pair(
                2*pair1.first - pair2.first,
                2*pair1.second - pair2.second
            )
        )

    private fun printArray(array: Array<CharArray>) {
        array.forEach { row ->
            row.forEach { print(it) }
            print("\n")
        }
        println()
    }

    private fun printAnntenasWithTheirAntinodes(antennas: Set<Pair<Int,Int>>, antinodes: Set<Pair<Int,Int>>) {
        for(row in 0 until 50) {
            for (col in 0 until 50) {
                val coordinates = Pair(row, col)
                if (coordinates in antennas && coordinates in antinodes)
                    print("%")
                else if (coordinates in antinodes)
                    print("#")
                else if (coordinates in antennas)
                    print("a")
                else print(".")
            }
            println()
        }
        println(antinodes.size)
        println()
        println()
    }
}