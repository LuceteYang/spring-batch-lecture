package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.*
import org.springframework.batch.core.repository.JobRepository
import org.springframework.stereotype.Component

@Component
class JobRepositoryListener(
    private val jobRepository: JobRepository
) : JobExecutionListener {
    override fun beforeJob(jobExecution: JobExecution) {
        TODO("Not yet implemented")
    }

    override fun afterJob(jobExecution: JobExecution) {
        val jobName = jobExecution.jobInstance.jobName
        val jobParameters = JobParametersBuilder().addString("name", "users2").toJobParameters()
        val lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters)
        lastJobExecution?.run {
            for (execution in this.stepExecutions) {
                val status: BatchStatus = execution.status
                println("status = $status")
                val exitStatus: ExitStatus = execution.exitStatus
                println("exitStatus = $exitStatus")
                val stepName: String = execution.stepName
                println("stepName = $stepName")
            }
        }
    }
}