package com.example.springbatch.springbatchlecture.controller

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

//@RestController
class JobLauncherController(
    private val job: Job,
    private val basicBatchConfigurer: BasicBatchConfigurer,
) {

    @PostMapping("/batch")
    @Throws(Exception::class)
    fun launch(@RequestBody member: Member): String {
        val jobParameters = JobParametersBuilder()
            .addString("id", member.id)
            .addDate("date", Date())
            .toJobParameters()

        val jobLauncher = basicBatchConfigurer.jobLauncher as SimpleJobLauncher // setTaskExecutor메소드로 타입을 설정하기 위해 SimpleJobLauncher로 타입퀘스팅
        jobLauncher.setTaskExecutor(SimpleAsyncTaskExecutor()) // 비동기적 실행
        jobLauncher.run(job, jobParameters)
        println("Job is completed")
        return "batch completed"
    }

    @GetMapping("/batch")
    fun launch(): String {
        return "batch completed"
    }

}

