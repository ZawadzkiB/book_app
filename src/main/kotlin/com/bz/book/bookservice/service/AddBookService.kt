package com.bz.book.bookservice.service

import com.bz.book.bookservice.repository.Book
import com.bz.book.bookservice.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class AddBookService(val bookRepository: BookRepository) {

    fun addBook(addBookRequest: AddBookRequest) = bookRepository.save(addBookRequest.toBook())

}

data class AddBookRequest(val author: String, val isbn: String, val pages: Int, val rate: Int, val title: String) {
    fun toBook(): Book = Book(author = author, isbn = isbn, pages = pages, rate = rate, title = title)
}
