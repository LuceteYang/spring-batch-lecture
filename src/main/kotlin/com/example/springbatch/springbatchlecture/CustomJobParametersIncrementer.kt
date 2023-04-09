package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersIncrementer
import java.text.SimpleDateFormat
import java.util.*

class CustomJobParametersIncrementer: JobParametersIncrementer {
    override fun getNext(parameters: JobParameters?): JobParameters {
        val id = SimpleDateFormat("yyyyMMdd-hhmmss").format(Date())

        return JobParametersBuilder().addString("run.id", id).toJobParameters()
    }
}