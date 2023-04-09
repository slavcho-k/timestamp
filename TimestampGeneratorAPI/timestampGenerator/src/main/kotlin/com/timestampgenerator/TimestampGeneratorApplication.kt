package com.timestampgenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TimestampGeneratorApplication

fun main(args: Array<String>) {
    runApplication<TimestampGeneratorApplication>(*args)
}
