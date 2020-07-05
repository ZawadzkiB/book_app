package com.bz.book.bookservice.service

import com.bz.book.bookservice.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class EditBookService(val bookRepository: BookRepository) {

    @Transactional
    fun editBook(bookId: UUID, bookRequest: BookRequest): BookResponse {
        val book = bookRepository.findByIdAndRemoveFalse(bookId).orElseThrow { BookNotFoundException("Book Not Found") }
        bookRequest.validateRequest()
        book.author = bookRequest.author
        book.isbn = bookRequest.isbn
        book.pages = bookRequest.pages
        book.rate = bookRequest.rate
        book.title = bookRequest.title
        return bookRepository.save(book).toResponse()
    }


}
