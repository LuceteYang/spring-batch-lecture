package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.JobParametersValidator
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SimpleJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    @Bean
    fun simpleJob() = jobBuilderFactory["simpleJob"]
        .start(taskletStep())
        .next(customTaskletStep())
        .next(simpleJobLastStep())
//        .validator(CustomJobParametersValidator())    // validator 는 뒤에꺼 하나만 되는구나..
        .validator(
            DefaultJobParametersValidator(
                arrayOf("name", "date"),
                arrayOf("age", "run.id")
            )
        )  // parameter 가능한거 다 선언되어야함
//        .incrementer(RunIdIncrementer())  // 이전 Parameter 에 Run.id 더한거와 새로 입력받은 Parameter merge 해서 리턴
        .incrementer(CustomJobParametersIncrementer())
//        .preventRestart() // 재시작을 하지 않음 restartable = false
        .build()

    @Bean
    fun taskletStep() = stepBuilderFactory["taskletStep"]
        .tasklet { stepContribution, chunkContext ->
//            jobParameters 값만 확인하기 위해서는 stepContribution, chunkContext 둘다 가져올수 있다.
//            stepContribution 청크 프로세스 변경 사항 버퍼링 후 StepExecution 상태를 업데이트함
            println("taskletStep start")

            val jobParameters = stepContribution.stepExecution.jobExecution.jobParameters
            println("name = ${jobParameters.getString("name")}")
            println("seq = ${jobParameters.getLong("seq")}")
            println("date = ${jobParameters.getDate("date")}")
            println("age = ${jobParameters.getDouble("age")}")
            // map 으로 확인 가능
            val jobParameters1 = chunkContext.stepContext.jobParameters
            println("name = ${jobParameters1.get("name")}")

            val jobExecutionContextKey =
                stepContribution.stepExecution.jobExecution.executionContext.get("jobExecutionContextKey")
            if (jobExecutionContextKey == null) {
                stepContribution.stepExecution.jobExecution.executionContext.put("jobExecutionContextKey", "JobHello")
            }
            println("jobExecutionContextKey : " + stepContribution.stepExecution.jobExecution.executionContext.get("jobExecutionContextKey"))

            val stepExecutionContextKey = stepContribution.stepExecution.executionContext.get("stepExecutionContextKey")
            if (stepExecutionContextKey == null) {
                stepContribution.stepExecution.executionContext.put("stepExecutionContextKey", "stepHello")
            }
            println("stepExecutionContextKey : " + stepContribution.stepExecution.executionContext.get("stepExecutionContextKey"))

            println("taskletStep end")
            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun customTaskletStep() = stepBuilderFactory["customTaskletStep"]
        .tasklet(CustomTasklet())
        .build()

    @Bean
    fun simpleJobLastStep() = stepBuilderFactory["simpleJobLastStep"]
        .tasklet { _, _ ->
            println("simpleJobLastStep executed")
            RepeatStatus.FINISHED
        }
        .build()
}