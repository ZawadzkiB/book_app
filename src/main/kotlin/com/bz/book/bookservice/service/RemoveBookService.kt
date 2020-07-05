package com.bz.book.bookservice.service

import com.bz.book.bookservice.repository.BookRepository
import com.bz.book.bookservice.repository.LastCommentsRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RemoveBookService(
        val bookRepository: BookRepository,
        val lastCommentsRepository: LastCommentsRepository,
        val commentsRepository: LastCommentsRepository
) {

    fun removeBook(bookId: UUID){
        lastCommentsRepository.deleteAllByBookId(bookId)
        commentsRepository.deleteAllByBookId(bookId)
        bookRepository.deleteById(bookId)
    }

}