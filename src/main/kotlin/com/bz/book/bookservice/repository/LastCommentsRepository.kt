package com.bz.book.bookservice.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Repository
interface LastCommentsRepository : CrudRepository<LastComments, UUID>{
        @Query(value = "select lc from LastComments lc join fetch lc.comment c where lc.bookId = :bookId")
        fun findAllByBookIdWithComment(@Param("bookId") bookId: UUID): MutableList<LastComments>
}

@Entity
data class LastComments(
        @Id
        val id: UUID = UUID.randomUUID(),
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "comment_id", referencedColumnName = "id")
        var comment: Comment,
        @Column(name = "book_id")
        val bookId: UUID
)