package com.bz.book.bookservice.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Repository
interface BookRepository : CrudRepository<Book, UUID>

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
        val lastComments: List<LastComments>? = emptyList()
)