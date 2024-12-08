package org.example.days.day_4

import org.example.days.DayTemplate

class Day4: DayTemplate() {
    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_4/input-data.txt"

        private const val PHRASE = "XMAS"
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        countXmas(content)
    }

    override fun executePartTwo() {
        val content = getContent(FILE_PATH)
        countCrossXmas(content)
    }

    private fun countCrossXmas(content: List<String>) {
        val contentAsArray = content.map { it.toCharArray() }.toTypedArray()
        val count = countCrossPhrases(contentAsArray)
        println(count)
    }

    private fun countXmas(content: List<String>) {
        val contentAsArray = content.map { it.toCharArray() }.toTypedArray()
        val count = countPhrases(contentAsArray)
        println(count)
    }

    private fun countPhrases(content: Array<CharArray>): Int {
        var phrasesCount = 0
        for (row in 0 until content.size) {
            for (col in 0 until content[0].size) {
                //for some reason I can't break only from the inner loop,
                // so I had to make the code inside an if. the inner@ annotation didn't work :(
                if (PHRASE.first() == content[row][col]) {
                    val listOfDirection = listOf(
                        checkUp(content, col, row),
                        checkDown(content, col, row),
                        checkLeft(content, col, row),
                        checkRight(content, col, row),
                        checkDownLeft(content, col, row),
                        checkDownRight(content, col, row),
                        checkUpLeft(content, col, row),
                        checkUpRight(content, col, row),
                    )
                    val numberOfMatchesInCurrentCoordinates = listOfDirection.filter { it == true }.size
                    phrasesCount += numberOfMatchesInCurrentCoordinates
                }
            }
        }
        return phrasesCount
    }

    private fun countCrossPhrases(content: Array<CharArray>): Int {
        var phrasesCount = 0
        for (row in 1 until content.size - 1) {
            for (col in 1 until content[0].size - 1 ) {
                if (PHRASE[2] == content[row][col]) {
                    val listOfDirection = listOf(
                        checkDownLeft(content, col + 2, row - 2),
                        checkDownRight(content, col - 2, row - 2),
                        checkUpLeft(content, col + 2 , row + 2),
                        checkUpRight(content, col - 2, row + 2),
                    )
                    println(listOfDirection)
                    val numberOfMatchesInCurrentCoordinates = listOfDirection.filter { it == true }.size
                    if (numberOfMatchesInCurrentCoordinates == 2 )
                        phrasesCount ++
                }
            }
        }
        return phrasesCount
    }

    private fun checkUp(content: Array<CharArray>, col: Int, row: Int): Boolean {
        if (row + 1 < PHRASE.length)
            return false
        PHRASE.drop(1).forEachIndexed { index, letter ->
            if(content[row - (index + 1)][col] != letter)
                return false
        }
        return true
    }

    private fun checkDown(content: Array<CharArray>, col: Int, row: Int): Boolean {
        if (row + PHRASE.length > content.size)
            return false
        PHRASE.drop(1).forEachIndexed { index, letter ->
            if(content[row + (index + 1)][col] != letter)
                return false
        }
        return true
    }

    private fun checkLeft(content: Array<CharArray>, col: Int, row: Int): Boolean {
        if (col + 1 < PHRASE.length)
            return false
        PHRASE.drop(1).forEachIndexed { index, letter ->
            if(content[row][col - (index + 1)] != letter)
                return false
        }
        return true
    }

    private fun checkRight(content: Array<CharArray>, col: Int, row: Int): Boolean {
        if (col + PHRASE.length > content.size)
            return false
        PHRASE.drop(1).forEachIndexed { index, letter ->
            if(content[row][col + (index + 1)] != letter)
                return false
        }
        return true
    }

    private fun checkDownLeft(content: Array<CharArray>, col: Int, row: Int): Boolean {
        if (row + PHRASE.length > content.size)
            return false
        if (col + 1 < PHRASE.length)
            return false
        PHRASE.drop(1).forEachIndexed { index, letter ->
            if(content[row + (index + 1)][col - (index + 1)] != letter)
                return false
        }
        return true
    }

    private fun checkDownRight(content: Array<CharArray>, col: Int, row: Int): Boolean {
        if (row + PHRASE.length > content.size)
            return false
        if (col + PHRASE.length > content.size)
            return false
        PHRASE.drop(1).forEachIndexed { index, letter ->
            if(content[row + (index + 1)][col + (index + 1)] != letter)
                return false
        }
        return true
    }

    private fun checkUpLeft(content: Array<CharArray>, col: Int, row: Int): Boolean {
        if (row + 1 < PHRASE.length)
            return false
        if (col + 1 < PHRASE.length)
            return false
        PHRASE.drop(1).forEachIndexed { index, letter ->
            if(content[row - (index + 1)][col - (index + 1)] != letter)
                return false
        }
        return true
    }

    private fun checkUpRight(content: Array<CharArray>, col: Int, row: Int): Boolean {
        if (row + 1 < PHRASE.length)
            return false
        if (col + PHRASE.length > content.size)
            return false
        PHRASE.drop(1).forEachIndexed { index, letter ->
            if(content[row - (index + 1)][col + (index + 1)] != letter)
                return false
        }
        return true
    }

}