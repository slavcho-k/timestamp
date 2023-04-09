package com.timestampgenerator.api

import com.timestampgenerator.domain.Timestamp
import com.timestampgenerator.service.TimestampService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class Controller(val service: TimestampService) {

    @GetMapping
    fun currentTimestamp(): ResponseEntity<Timestamp> {
        return ResponseEntity.ok(service.getCurrentTimestamp())
    }

    @GetMapping("/{dateFormat}")
    fun fixedTimestamp(@PathVariable dateFormat: String): ResponseEntity<Any> {
        return ResponseEntity.ok(service.getFixedTimestamp(dateFormat))
    }
}