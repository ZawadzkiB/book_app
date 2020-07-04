package com.bz.book.bookservice.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

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