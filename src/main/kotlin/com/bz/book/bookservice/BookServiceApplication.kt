package com.bz.book.bookservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate




@SpringBootApplication
class BookServiceApplication

fun main(args: Array<String>) {
    runApplication<BookServiceApplication>(*args)
}