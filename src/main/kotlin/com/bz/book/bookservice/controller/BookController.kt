package com.bz.book.bookservice.controller

import com.bz.book.bookservice.service.BookService
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/book")
class BookController(val bookService: BookService) {

    @GetMapping
    fun getBooks(
            @RequestParam(name = "page", defaultValue = "0") page: Int,
            @RequestParam(name = "size", defaultValue = "10") size: Int) =
            bookService.findBooks(PageRequest.of(page, size))
}