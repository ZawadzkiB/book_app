package com.bz.book.bookservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@SpringBootApplication
class BookServiceApplication

fun main(args: Array<String>) {
    runApplication<BookServiceApplication>(*args)
}

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

@Repository
interface CommentRepository : CrudRepository<Comment, UUID>

@Entity
data class Comment(
        @Id
        val id: UUID = UUID.randomUUID(),
        val bookId: UUID,
        val nickname: String,
        val comment: String,
        val addTime: LocalDateTime
)

@Repository
interface LastCommentsRepository : CrudRepository<LastComments, UUID>

@Entity
data class LastComments(
        @Id
        val id: UUID = UUID.randomUUID(),
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "comment_id", referencedColumnName = "id")
        val comment: Comment,
        @Column(name = "book_id")
        val bookId: UUID
)