package com.bz.book.bookservice

import org.testcontainers.containers.PostgreSQLContainer

class PostgresSetup private constructor() : PostgreSQLContainer<PostgresSetup?>(IMAGE_VERSION) {
    override fun start() {
        super.start()
        System.setProperty("DB_URL", container!!.jdbcUrl)
        System.setProperty("DB_USERNAME", container!!.username)
        System.setProperty("DB_PASSWORD", container!!.password)
    }

    companion object {
        private const val IMAGE_VERSION = "postgres:11.1-alpine"
        private var container: PostgresSetup? = null
        val instance: PostgresSetup?
            get() {
                if (container == null) {
                    container = PostgresSetup()
                }
                return container
            }
    }
}