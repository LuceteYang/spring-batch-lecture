package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class SpringBatchApplication

fun main(args: Array<String>) {
	runApplication<SpringBatchApplication>(*args)
}
