package com.bz.book.bookservice.controller

import com.bz.book.bookservice.service.AddBookRequest
import com.bz.book.bookservice.service.AddBookService
import com.bz.book.bookservice.service.FindBookService
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/book")
class BookController(
        val findBookService: FindBookService,
        val addBookService: AddBookService
) {

    @GetMapping
    fun getBooks(
            @RequestParam(name = "page", defaultValue = "0") page: Int,
            @RequestParam(name = "size", defaultValue = "10") size: Int) =
            findBookService.findBooks(PageRequest.of(page, size))

    @GetMapping("/{id}")
    fun getBook(@PathVariable(name = "id") id: UUID) = findBookService.findBookById(id)

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun addBook(@RequestBody addBookRequest: AddBookRequest) = addBookService.addBook(addBookRequest)
}