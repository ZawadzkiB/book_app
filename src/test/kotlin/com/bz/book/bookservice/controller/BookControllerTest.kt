package com.bz.book.bookservice.controller

import com.bz.book.bookservice.IntegrationBase
import com.bz.book.bookservice.repository.Book
import com.bz.book.bookservice.repository.BookRepository
import com.bz.book.bookservice.service.AddBookRequest
import com.bz.book.bookservice.service.FindBookService
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort
import java.util.*


class BookControllerTest : IntegrationBase() {

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var findBookService: FindBookService

    @LocalServerPort
    var randomServerPort = 0

    @AfterEach
    fun cleanUp() {
        bookRepository.deleteAll()
    }

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

    @Test
    fun `get book by id should return 200`() {
        val savedBook = bookRepository.save(Book(author = "testAuthor1", isbn = "testIsbn", pages = 123, rate = 5, title = "book1"))

        given().port(randomServerPort).basePath("/api/book/${savedBook.id}")
                .`when`()
                .get()
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(savedBook.id.toString()))
    }

    @Test
    fun `get book by missing id should return 404`() {
        given().port(randomServerPort).basePath("/api/book/{id}")
                .pathParam("id", UUID.randomUUID())
                .`when`()
                .get()
                .then()
                .statusCode(404)
    }

    @Test
    fun `get book by wrong id should return 400`() {
        given().port(randomServerPort).basePath("/api/book/dupa")
                .`when`()
                .get()
                .then()
                .statusCode(400)
    }

    @Test
    fun `add book should return 201`() {
        val bookRequest = AddBookRequest(author = "testAuthor1", isbn = "testIsbn", pages = 123, rate = 5, title = "book1")

        val bookId = given().port(randomServerPort).basePath("/api/book")
                .body(bookRequest)
                .contentType(ContentType.JSON)
                .`when`()
                .post()
                .then()
                .statusCode(201)
                .extract().path<String>("id")

        val bookResponse = findBookService.findBookById(UUID.fromString(bookId))
        assertThat(bookResponse).isEqualToIgnoringGivenFields(bookRequest, "id", "comments")
    }
}


