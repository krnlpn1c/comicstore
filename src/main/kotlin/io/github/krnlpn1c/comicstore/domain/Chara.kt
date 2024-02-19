package io.github.krnlpn1c.comicstore.domain

import jakarta.persistence.*

@Entity
@Table(name = "character")
data class Chara(
        @Column
        val name: String,
) {
        @Id
        @SequenceGenerator(name="character_id_seq",
            sequenceName="character_id_seq",
            allocationSize=1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="character_id_seq")
        var id: Int = 0
        @ManyToMany(mappedBy = "characters")
        val books: MutableList<Book> = mutableListOf()
}
