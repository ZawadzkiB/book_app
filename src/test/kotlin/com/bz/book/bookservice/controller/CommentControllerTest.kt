package com.bz.book.bookservice.controller

import com.bz.book.bookservice.IntegrationBase
import com.bz.book.bookservice.repository.Book
import com.bz.book.bookservice.repository.BookRepository
import com.bz.book.bookservice.repository.CommentRepository
import com.bz.book.bookservice.repository.LastCommentsRepository
import com.bz.book.bookservice.service.CommentRequest
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort

class CommentControllerTest : IntegrationBase() {

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var lastCommentRepository: LastCommentsRepository

    @Autowired
    lateinit var commentsRepository: CommentRepository

    @LocalServerPort
    var randomServerPort = 0

    @AfterEach
    fun cleanUp() {
        lastCommentRepository.deleteAll()
        commentsRepository.deleteAll()
        bookRepository.deleteAll()
    }

    @Test
    fun `add comment should return 201`(){
        val book = bookRepository.save(Book(author = "testAuthor1", isbn = "testIsbn", pages = 123, rate = 5, title = "book1"))
        RestAssured.given().port(randomServerPort).basePath("/api/comment")
                .body(CommentRequest(nickname = "nick1", comment = "comment1", bookId = book.id))
                .contentType(ContentType.JSON)
                .`when`()
                .post()
                .then()
                .statusCode(201)
    }

}