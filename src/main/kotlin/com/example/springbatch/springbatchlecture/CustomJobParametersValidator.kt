package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.JobParametersValidator

class CustomJobParametersValidator : JobParametersValidator {
    override fun validate(parameters: JobParameters?) {
        println("CustomJobParametersValidator name: ${parameters?.getString("name")}")
        if(parameters?.getString("name") == null) {
            throw JobParametersInvalidException("name parameters is not found")
        }
    }
}