package io.github.krnlpn1c.comicstore.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NamedQuery
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime

const val BOOKS_QUERY = "books_query"

@NamedQuery(name = BOOKS_QUERY, query = """
        SELECT b FROM Book b
            JOIN b.authors a 
            JOIN b.characters ch 
        WHERE (:bookName IS NULL OR lower(b.name) LIKE :bookName)
            AND
             (:authorName IS NULL OR lower(a.name) LIKE :authorName)
            AND 
             (:characterName IS NULL OR lower(ch.name) LIKE :characterName)
        order by b.releaseDt desc
""")

@Entity
@Table(name = "book")
data class Book(
    @Column
    val name: String,
    @Column
    var publisher: String,
    @Column
    val releaseDt: OffsetDateTime
) {
    @Id
    @SequenceGenerator(name="book_id_seq",
        sequenceName="book_id_seq",
        allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator="book_id_seq")
    var id: Int = 0
    @Column
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "book_author",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")],
    )
    val authors: MutableList<Author> = mutableListOf()
    @Column
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "book_character",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "character_id")],
    )
    val characters: MutableList<Chara> = mutableListOf()

    @CreationTimestamp
    lateinit var createDt: OffsetDateTime
    @UpdateTimestamp
    lateinit var updateDt: OffsetDateTime

    fun addAuthor(author: Author) {
        this.authors.add(author)
        author.books.add(this)
    }

    fun addCharacter(character: Chara) {
        this.characters.add(character)
        character.books.add(this)
    }
}
