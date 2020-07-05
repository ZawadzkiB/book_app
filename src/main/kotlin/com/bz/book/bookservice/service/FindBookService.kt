package com.bz.book.bookservice.service

import com.bz.book.bookservice.repository.Book
import com.bz.book.bookservice.repository.BookRepository
import com.bz.book.bookservice.repository.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException
import java.util.*

@Service
class FindBookService(val bookRepository: BookRepository) {

    @Transactional
    fun findBooks(page: PageRequest): Page<BookResponse> = bookRepository.findAll(page).toResponse()

    fun findBookById(id: UUID) = bookRepository.findById(id)
            .orElseThrow { BookNotFoundException("Book Not Found") }
            .toResponse()
}

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class BookNotFoundException(message: String?) : RuntimeException(message)

fun Page<Book>.toResponse() = PageImpl(this.content.map { it.toResponse() }, this.pageable, this.totalElements)

fun Book.toResponse() = BookResponse(this.id, this.isbn, this.title, this.author, this.lastComments.map { cm -> cm.comment }, this.pages, this.rate)


data class BookResponse(
        val id: UUID,
        val isbn: String,
        val title: String,
        val author: String,
        val comments: List<Comment>,
        val pages: Int,
        val rate: Int)