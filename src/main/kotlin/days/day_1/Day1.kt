package org.example.days.day_1

import org.example.days.DayTemplate
import java.io.File
import java.util.PriorityQueue

class Day1 : DayTemplate() {
    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_1/input-data.txt"
        private const val DIVIDER = "   "
    }

    override fun execute() {
        val listA = PriorityQueue<Int>()
        val listB = PriorityQueue<Int>()
        val content = getContent(FILE_PATH)

        parseContent(content, listA, listB)
        val distance = countDistance(listA, listB)

        print(distance)
    }

    override fun executePartTwo() {
        val listA = PriorityQueue<Int>()
        val listB = PriorityQueue<Int>()
        val content = getContent(FILE_PATH)

        parseContent(content, listA, listB)

        val countMap = mutableMapOf<Int,Int>()
        countListAElementOccurance(countMap, listA.toSet(), listB.toList())

        val score = countScore(countMap)
        println(score)
    }

    private fun parseContent(content: List<String>, listA: PriorityQueue<Int>, listB: PriorityQueue<Int>) {
        content.forEach { line ->
            line.split(DIVIDER).apply {
                listA.add(first().toInt())
                listB.add(last().toInt())
            }
        }
    }

    private fun countDistance(listA: PriorityQueue<Int>, listB: PriorityQueue<Int>): Int {
        var distance = 0

        repeat(listA.size) {
//            println(listA.peek().toString() + "   " + listB.peek().toString())
            val diff = listA.poll() - listB.poll()
            if (diff > 0)
                distance += diff
            else
                distance -= diff
        }

        return distance
    }

    private fun countListAElementOccurance(countMap: MutableMap<Int,Int>, listA: Set<Int>, listB: List<Int> ) {
        listA.forEach { element ->
            val count = listB.filter { element == it }.size
            if (count > 0)
                countMap[element] = count
        }
    }

    private fun countScore(countMap: Map<Int,Int>): Int {
        var score = 0
        countMap.forEach { (element, count) ->
            score += element * count
        }
        return score
    }

}