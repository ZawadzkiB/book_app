package com.bz.book.bookservice.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Repository
interface CommentRepository : CrudRepository<Comment, UUID>

@Entity
data class Comment(
        @Id
        val id: UUID = UUID.randomUUID(),
        val bookId: UUID,
        val nickname: String,
        val comment: String,
        val addTime: LocalDateTime = LocalDateTime.now()
)