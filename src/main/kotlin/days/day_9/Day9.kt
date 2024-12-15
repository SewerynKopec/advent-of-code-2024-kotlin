package org.example.days.day_9

import org.example.days.DayTemplate

class Day9: DayTemplate() {

    companion object {
        private const val FILE_PATH = "src/main/kotlin/days/day_9/input-data.txt"
        private const val EMPTY = "."
    }

    override fun execute() {
        val content = getContent(FILE_PATH)
        val sequence = content.first()
        val diskSpace = getDiskSpace(sequence)
        val optimizedDiskSpace = optimizeDiskSpace(diskSpace)
        val checkSum = getCheckSum(optimizedDiskSpace)
        println(diskSpace.joinToString())
        println(optimizedDiskSpace.joinToString())
        println(checkSum)
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }

    private fun getDiskSpace(sequence: String): List<String> {
        var diskSpace = mutableListOf<String>()
        sequence.forEachIndexed { index, number ->
            val nextValue =
                if (index % 2 == 0)
                    (index/2).toString()
                else {
                    EMPTY
                }
            repeat(number.digitToInt()){
                diskSpace.add(nextValue)
            }
        }
        return diskSpace.toList()
    }

    private fun optimizeDiskSpace(diskSpace: List<String>): List<String> {
        val optimizedDiskSpace = diskSpace.toMutableList()
        val iterator = diskSpace.listIterator()
        val reverseIterator = diskSpace.listIterator(diskSpace.size)
        var nextEmpty: String? = null
        var nextEmptyIndex = -1
        var nextFile: String? = null
        var nextFileIndex = -1
        while (iterator.nextIndex() < reverseIterator.previousIndex()) {
            while (nextEmpty != EMPTY) {
                nextEmptyIndex = iterator.nextIndex()
                nextEmpty = iterator.next()
            }
            while (nextFile == null || nextFile == EMPTY) {
                nextFileIndex = reverseIterator.previousIndex()
                nextFile= reverseIterator.previous()
            }
            if(nextEmptyIndex < nextFileIndex) {
                optimizedDiskSpace.set(nextEmptyIndex, nextFile)
                optimizedDiskSpace.set(nextFileIndex, nextEmpty)
            }
            nextEmpty = null
            nextFile = null
        }
        return optimizedDiskSpace
    }

    private fun getCheckSum(diskSpace: List<String>): Long {
        var sum = 0L
        diskSpace.forEachIndexed { index, id ->
            if (id == EMPTY)
                return@forEachIndexed
            sum += index * id.toInt()
        }
        return sum
    }
}