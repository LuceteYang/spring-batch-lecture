package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.support.ListItemReader
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class TaskletStepJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    @Bean
    fun taskletStepJob() = jobBuilderFactory["taskletStepJob"]
        .start(taskletStep())
        .next(chunkStep())
        .build()

    fun taskletStep() = stepBuilderFactory["taskletStep"]
        .tasklet { _, _ ->
            println("taskletStep executed")
            RepeatStatus.FINISHED
        }
        .listener(CustomStepExecutionListener())
        .allowStartIfComplete(true)    // Step 의 이전 성공 여부와 상관없이 항상 step 을 실행하기 위한 설정
        .build()

    fun chunkStep() = stepBuilderFactory["chunkStep"]
        .chunk<String, String>(10)
        .reader(ListItemReader(listOf("item1", "item2", "item3", "item4", "item5")))
        .processor(ItemProcessor { return@ItemProcessor it.uppercase(Locale.getDefault()) })
        .writer { items -> items.forEach { println(it) } }
//        .writer { items -> throw RuntimeException("step2 was failed") }
        .startLimit(5) // 실패시 실행 가능 횟수 조정
        .build()
}

class CustomStepExecutionListener : StepExecutionListener {
    override fun beforeStep(stepExecution: StepExecution) {
        val stepName: String = stepExecution.stepName
        println("stepName = $stepName start")
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        val stepName: String = stepExecution.stepName
        val exitStatus: ExitStatus = stepExecution.exitStatus
        println("stepName = $stepName end  exitStatus : $exitStatus")
        // exitStatus 조작 가능
        //return ExitStatus.FAILED
        return null
    }
}