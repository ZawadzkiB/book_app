package com.bz.book.bookservice.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Repository
interface BookRepository : CrudRepository<Book, UUID>{
        @Query(
                value = "select distinct b from Book b left join fetch b.lastComments lc left join fetch lc.comment c where b.remove = false",
                countQuery = "select count(b) from Book b where b.remove = false"
        )
        fun findAllWithComments(pageable: Pageable): Page<Book>

        fun findByIdAndRemoveFalse(id: UUID) : Optional<Book>
}

@Entity
data class Book(
        @Id
        val id: UUID = UUID.randomUUID(),
        var isbn: String,
        var title: String,
        var author: String,
        var pages: Int,
        var rate: Int,
        @OneToMany(fetch = FetchType.LAZY)
        @JoinColumn(name = "book_id")
        val lastComments: List<LastComments> = emptyList(),
        var remove: Boolean = false

)