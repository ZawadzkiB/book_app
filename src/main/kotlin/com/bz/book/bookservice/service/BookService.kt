package com.bz.book.bookservice.service

import com.bz.book.bookservice.repository.Book
import com.bz.book.bookservice.repository.BookRepository
import com.bz.book.bookservice.repository.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(val bookRepository: BookRepository){

    fun findBooks(page: PageRequest): Page<Book> = bookRepository.findAllWithComments(page)

}

data class BookResponse(val id: UUID, val title: String, val comments: List<Comment>)