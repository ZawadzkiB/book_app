package com.bz.book.bookservice.repository

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Repository
interface BookRepository : CrudRepository<Book, UUID>{
        @Query(value = "select distinct b from Book b left join b.lastComments lc left join lc.comment c")
        fun findAllWithComments(pageable: Pageable): Page<Book>
}

@Entity
data class Book(
        @Id
        val id: UUID = UUID.randomUUID(),
        val isbn: String,
        val title: String,
        val author: String,
        val pages: Int,
        val rate: Int,
        @OneToMany(fetch = FetchType.LAZY)
        @JoinColumn(name = "book_id")
        @JsonIgnore
        val lastComments: List<LastComments> = emptyList()
)