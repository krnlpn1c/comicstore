package io.github.krnlpn1c.comicstore.controller.v2

import io.github.krnlpn1c.comicstore.service.AuthorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/authors")
class AuthorControllerV2(
    private val authorService: AuthorService
) {
    @GetMapping("/{authorId}")
    fun authorDetails(@PathVariable authorId: Int) {
        authorService.authorDetails(authorId)
    }
}