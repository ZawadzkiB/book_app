package com.bz.book.bookservice.service

import com.bz.book.bookservice.repository.Book
import com.bz.book.bookservice.repository.BookRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@Service
class AddBookService(val bookRepository: BookRepository) {

    fun addBook(addBookRequest: AddBookRequest) = bookRepository
            .save(validateRequest(addBookRequest).toBook())
            .toResponse()

    fun validateRequest(addBookRequest: AddBookRequest): AddBookRequest {
        if(addBookRequest.rate < 0 || addBookRequest.rate > 5){
            throw BookValidationException("Rate should be from 0 to 5")
        }
        if(!addBookRequest.isbn.isValidISBN()){
            throw BookValidationException("ISBN is not valid")
        }
        return addBookRequest
    }
}

private fun String.isValidISBN(): Boolean {
    return Regex("^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$")
            .matches(this)
}

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class BookValidationException(message: String?) : RuntimeException(message)

data class AddBookRequest(val author: String, val isbn: String, val pages: Int, val rate: Int, val title: String) {
    fun toBook(): Book = Book(author = author, isbn = isbn, pages = pages, rate = rate, title = title)
}
