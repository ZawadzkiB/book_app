package com.bz.book.bookservice.service

import com.bz.book.bookservice.repository.BookRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RemoveBookService(val bookRepository: BookRepository) {

    fun removeBook(bookId: UUID){
        val bookToRemove = bookRepository.findById(bookId)
                .orElseThrow{BookNotFoundException("Book Not Found")}
        bookToRemove.remove = true
        bookRepository.save(bookToRemove)
    }

}