package com.bz.book.bookservice.controller

import com.bz.book.bookservice.IntegrationBase
import com.bz.book.bookservice.repository.*
import com.bz.book.bookservice.service.BasicCommentResponse
import com.bz.book.bookservice.service.CommentRequest
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
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
        val savedBook = bookRepository.save(Book(author = "testAuthor1", isbn = "testIsbn", pages = 123, rate = 5, title = "book1"))
        val comment = CommentRequest(nickname = "nick1", comment = "comment1", bookId = savedBook.id)

        RestAssured.given().port(randomServerPort).basePath("/api/comment")
                .body(comment)
                .contentType(ContentType.JSON)
                .`when`()
                .post()
                .then()
                .statusCode(201)

        val lastComments = RestAssured.given().port(randomServerPort).basePath("/api/book/${savedBook.id}")
                .`when`()
                .get()
                .then()
                .statusCode(200)
                .body("comments[0].nickname", Matchers.equalTo(comment.nickname))
                .body("comments[0].comment", Matchers.equalTo(comment.comment))
                .extract().jsonPath().getList<BasicCommentResponse>("comments")


        assertThat(lastComments.size).isEqualTo(1)
    }

}