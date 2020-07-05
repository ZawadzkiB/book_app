package com.bz.book.bookservice.controller

import com.bz.book.bookservice.service.FindBookService
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/book")
class BookController(val bookService: FindBookService) {

    @GetMapping
    fun getBooks(
            @RequestParam(name = "page", defaultValue = "0") page: Int,
            @RequestParam(name = "size", defaultValue = "10") size: Int) =
            bookService.findBooks(PageRequest.of(page, size))

    @GetMapping("/{id}")
    fun getBook(@PathVariable(name = "id") id: UUID) = bookService.findBookById(id)
}