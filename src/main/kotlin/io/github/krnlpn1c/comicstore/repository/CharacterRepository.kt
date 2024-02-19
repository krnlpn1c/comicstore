package io.github.krnlpn1c.comicstore.repository

import io.github.krnlpn1c.comicstore.domain.Chara
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CharacterRepository: JpaRepository<Chara, Int> {
    fun findAllByNameIn(authors: Set<String>): List<Chara>
}
