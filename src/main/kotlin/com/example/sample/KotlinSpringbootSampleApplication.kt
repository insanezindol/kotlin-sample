package com.example.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    exclude = [
        DataSourceAutoConfiguration::class,
        DataSourceTransactionManagerAutoConfiguration::class,
        HibernateJpaAutoConfiguration::class
    ]
)
//@EnableJpaRepositories(basePackages = ["com.example.sample.repositories.h2"])
class KotlinSpringbootSampleApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringbootSampleApplication>(*args)
}
