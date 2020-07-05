package com.bz.book.bookservice.service

import com.bz.book.bookservice.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class EditBookService(val bookRepository: BookRepository) {

    @Transactional
    fun editBook(bookId: UUID, editBookRequest: EditBookRequest): BookResponse {
        val book = bookRepository.findByIdAndRemoveFalse(bookId).orElseThrow { BookNotFoundException("Book Not Found") }
        book.author = editBookRequest.author
        book.isbn = editBookRequest.isbn
        book.pages = editBookRequest.pages
        book.rate = editBookRequest.rate
        book.title = editBookRequest.title
        return bookRepository.save(book).toResponse()
    }


}

data class EditBookRequest(val author: String, val isbn: String, val pages: Int, val rate: Int, val title: String)
