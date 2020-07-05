package com.bz.book.bookservice.service

import com.bz.book.bookservice.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class CommentsService(val commentRepository: CommentRepository,
                      val lastCommentsRepository: LastCommentsRepository,
                      val bookRepository: BookRepository) {

    @Transactional
    fun addComment(commentRequest: CommentRequest): CommentResponse {
        checkIfCanAddComment(commentRequest.bookId)
        val comment = commentRepository.save(commentRequest.toComment())
        addLastComments(comment)
        return comment.toResponse()
    }

    private fun addLastComments(comment: Comment) {
        val lastComments = lastCommentsRepository.findAllByBookIdWithComment(comment.bookId)
        if (lastComments.size < 5) {
            lastCommentsRepository.save(LastComments(comment = comment, bookId = comment.bookId))
        } else {
            lastComments.minBy { it.comment.addTime }?.let {
                it.comment = comment
                lastCommentsRepository.save(it)
            }
        }
    }

    private fun checkIfCanAddComment(bookId: UUID){
        bookRepository.findById(bookId)
                .orElseThrow { BookNotFoundException("Book Not Found") }
                .let {
                    if(it.remove){
                        throw BookNotFoundException("Book Was Removed")
                    }
                }
    }
}

fun Comment.toResponse() = CommentResponse(
        id = this.id,
        bookId = this.bookId,
        nickname = this.nickname,
        comment = this.comment,
        addTime = this.addTime
)

data class CommentRequest(val bookId: UUID,
                          val nickname: String,
                          val comment: String) {
    fun toComment(): Comment = Comment(bookId = bookId, nickname = nickname, comment = comment)
}

data class CommentResponse(
        val id: UUID,
        val bookId: UUID,
        val nickname: String,
        val comment: String,
        val addTime: LocalDateTime
)
