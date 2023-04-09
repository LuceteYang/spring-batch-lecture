package com.example.springbatch.springbatchlecture

import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer
import org.springframework.boot.autoconfigure.batch.BatchProperties
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class CustomBatchConfigurer(
    private val properties: BatchProperties,
    private val dataSource: DataSource,
    private val transactionManagerCustomizers: TransactionManagerCustomizers
) : BasicBatchConfigurer(properties, dataSource, transactionManagerCustomizers) {


    @Throws(Exception::class)
    override fun createJobRepository(): JobRepository {
        val factory = JobRepositoryFactoryBean()
        factory.setDataSource(dataSource)
        factory.transactionManager = transactionManager
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE") // isolation 수준, 기본값은 “ISOLATION_SERIALIZABLE”
        factory.setTablePrefix("BATCH_") // 테이블 Prefix, 기본값은 “BATCH_”,
        // BATCH_JOB_EXECUTION 가 SYSTEM_JOB_EXECUTION 으로 변경됨
        // 실제 테이블명이 변경되는 것은 아니다
        factory.setMaxVarCharLength(1000)
        return factory.getObject() 
    }
}