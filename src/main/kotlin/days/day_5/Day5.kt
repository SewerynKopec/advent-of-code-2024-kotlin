package org.example.days.day_5

import org.example.days.DayTemplate
import java.util.HashMap

class Day5: DayTemplate(){

    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_5/input-data.txt"
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        val orderingRules = getOrderingRules(content)
        val listOfUpdates = getListOfUpdates(content)
        val listOfRightOrderedUpdates = checkOrderOfUpdates(orderingRules, listOfUpdates)
        val sum = sumMiddleElements(listOfRightOrderedUpdates)
        println(sum)
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }

    private fun getOrderingRules(content: List<String>): Map<Int, HashSet<Int>> {
        val rulesSection = content.subList(0, content.indexOf("") - 1)
        val valuePairs = rulesSection.map { row ->
            row.split("|").let { Pair(it.first().toInt(), it.last().toInt()) }
        }
        val mapOfRules = valuePairs.map { it.first }.associateWith { mutableListOf<Int>() }
        valuePairs.forEach { (key, value) ->
            mapOfRules[key]!!.add(value)
        }
        return mapOfRules.mapValues { it.value.toHashSet() }
    }

    private fun getListOfUpdates(content: List<String>): List<List<Int>> {
        val rawUpdatesList = content.subList(content.indexOf("") + 1, content.lastIndex)
        val updatesList = rawUpdatesList .map { updates ->
                updates.split(",") .map {
                    update -> update.toInt()
                }
            }
        return updatesList
    }

    private fun checkOrderOfUpdates(orderingRules: Map<Int, HashSet<Int>>, listOfUpdates: List<List<Int>>): List<List<Int>> =
        listOfUpdates.filter { updates ->
            updates.none { update ->
                val updatesSoFar = updates.subList(0, updates.indexOf(update))
                updatesSoFar.any { updateSoFar ->
                    orderingRules[update]?.contains(updateSoFar) == true
                }
            }
        }

    private fun sumMiddleElements(listOfRightOrderedUpdates: List<List<Int>>): Int =
        listOfRightOrderedUpdates.map { updates ->  updates.elementAt((updates.size - 1) /2) }.sum()

}