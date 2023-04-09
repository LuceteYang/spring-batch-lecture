package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.util.*

// batch.job.enabled false 로 해야 이게 실행됨
//@Component
class JobRunner(
    private val jobLauncher: JobLauncher,
    private val job: Job,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val jobParameters = JobParametersBuilder()
            .addString("name", "user2")
            .addLong("seq", 2L)
            .addDate("date", Date())
            .addDouble("age",16.5)
            .toJobParameters()
        jobLauncher.run(job, jobParameters)
    }
}