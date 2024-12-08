package org.example.days

import java.io.File

abstract class DayTemplate {

    protected fun getContent(filePath: String): List<String> {
        return File(filePath).readLines()
    }

    abstract fun execute()

    abstract fun executePartTwo()
}