package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor
import org.springframework.batch.core.step.job.JobParametersExtractor
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobStepConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    @Bean
    fun parentJob() = jobBuilderFactory["parentJob"]
        .start(jobStep())
        .next(parentJobStep2())
        .build()

    @Bean
    fun jobStep() = stepBuilderFactory["jobStep"]
        .job(childJob())
        .parametersExtractor(jobParametersExtractor())
        .listener(stepExecutionListener())
        .build()

    @Bean
    fun stepExecutionListener(): StepExecutionListener {
        return object : StepExecutionListener {
            override fun beforeStep(stepExecution: StepExecution) {
                stepExecution.executionContext.putString("name", "user1")
            }

            override fun afterStep(stepExecution: StepExecution): ExitStatus? {
                return stepExecution.exitStatus;
            }
        }
    }

    private fun jobParametersExtractor(): JobParametersExtractor {  // execution context 안에 있는 key 를 가져올수 있음
        val extractor = DefaultJobParametersExtractor()
        extractor.setKeys(arrayOf("name"))
        return extractor
    }

    @Bean
    fun childJob() = jobBuilderFactory["childJob"]
        .start(childJobStep1())
        .build()


    @Bean
    fun childJobStep1() = stepBuilderFactory["childJobStep1"]
        .tasklet { _, _ ->
            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun parentJobStep2() = stepBuilderFactory["parentJobStep2"]
        .tasklet { _, _ ->
            RepeatStatus.FINISHED
        }.build()

}