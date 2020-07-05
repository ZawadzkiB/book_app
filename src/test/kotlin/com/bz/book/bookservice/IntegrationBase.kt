package com.bz.book.bookservice

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.lifecycle.Startables
import java.util.stream.Stream

@ActiveProfiles(value = ["it"])
@ExtendWith(SpringExtension::class)
@Testcontainers
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [DbInitializer::class])
class IntegrationBase

class DbInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        val postgres = PostgresSetup.instance
    }
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        Startables.deepStart(Stream.of(postgres)).join();
    }
}