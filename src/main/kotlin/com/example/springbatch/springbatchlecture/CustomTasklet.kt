package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import java.lang.RuntimeException

class CustomTasklet: Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        println("CustomTasklet start")
//        chunkContext.stepContext.stepExecution.status = BatchStatus.FAILED
//        contribution.exitStatus = ExitStatus.STOPPED
        println("CustomTasklet end")
        return RepeatStatus.FINISHED
    }
}