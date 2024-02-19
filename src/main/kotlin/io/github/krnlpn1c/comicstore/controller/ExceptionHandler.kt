package io.github.krnlpn1c.comicstore.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

val logger = KotlinLogging.logger {}

@ControllerAdvice
class Handler {

    @ExceptionHandler
    fun handle(e: Exception): ResponseEntity<Any> {
        logger.error(e) { e.message }
        return ResponseEntity.internalServerError().build()
    }
}