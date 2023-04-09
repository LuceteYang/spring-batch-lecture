package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlowJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    @Bean
    fun flowJob() = jobBuilderFactory["flowJob"]
        .start(flow())
        .next(step3())
        .end()  // Flow 는 end 를 해줘야함
        .build()

    @Bean
    fun flow() = FlowBuilder<Flow>("FlowStep")
        .start(step1())
        .next(step2())
        .end()

    @Bean
    fun step1() = stepBuilderFactory["step1"]
        .tasklet { _, _ ->
            println("step1 executed")
            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun step2() = stepBuilderFactory["step2"]
        .tasklet { _, _ ->
            println("step2 executed")
            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun step3() = stepBuilderFactory["step3"]
        .tasklet { _, _ ->
            println("step3 executed")
            RepeatStatus.FINISHED
        }.build()
}