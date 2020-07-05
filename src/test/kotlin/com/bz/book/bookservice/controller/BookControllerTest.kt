package com.bz.book.bookservice.controller

import com.bz.book.bookservice.IntegrationBase
import com.bz.book.bookservice.repository.Book
import com.bz.book.bookservice.repository.BookRepository
import io.restassured.RestAssured.given
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort


class BookControllerTest : IntegrationBase() {

    @Autowired
    lateinit var bookRepository: BookRepository

    @LocalServerPort
    var randomServerPort = 0

    @Test
    fun `get books without comments should return 200`() {
        bookRepository.save(Book(author = "testAuthor1", isbn = "testIsbn", pages = 123, rate = 5, title = "book1"))
        assertThat(bookRepository.count()).isEqualTo(1)

        val body = given().port(randomServerPort).basePath("/api/book")
                .`when`()
                .get()
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList<Book>("content")
        assertThat(body.size).isEqualTo(1)
    }

}


