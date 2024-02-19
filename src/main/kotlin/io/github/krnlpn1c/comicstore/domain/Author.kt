package io.github.krnlpn1c.comicstore.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime

@Entity
@Table(name = "author")
data class Author(
        @Column
        val name: String,
) {
        @Id
        @SequenceGenerator(name="author_id_seq",
            sequenceName="author_id_seq",
            allocationSize=1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="author_id_seq")
        var id: Int = 0

        @ManyToMany(mappedBy = "authors")
        val books: MutableList<Book> = mutableListOf()

        @CreationTimestamp
        lateinit var createDt: OffsetDateTime
        @UpdateTimestamp
        lateinit var updateDt: OffsetDateTime
}
