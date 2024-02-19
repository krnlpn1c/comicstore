package io.github.krnlpn1c.comicstore.service

import io.github.krnlpn1c.comicstore.domain.Chara
import io.github.krnlpn1c.comicstore.repository.CharacterRepository
import org.springframework.stereotype.Service

@Service
class CharacterService(
    private val characterRepository: CharacterRepository
) {
    fun findCharacters(characters: Set<String>): List<Chara> {
        return characterRepository.findAllByNameIn(characters)
    }
}