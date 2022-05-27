package com.admiral.demo.filters

import org.junit.runner.Description
import org.junit.runner.manipulation.Filter

interface ClickerTest

class ClickerTestFilter : Filter() {
    override fun shouldRun(description: Description): Boolean =
        ClickerTest::class.java.isAssignableFrom(description.testClass)

    override fun describe(): String =
        "all tests implementing com.admiral.demo.filters.ClickerTest interface"
}