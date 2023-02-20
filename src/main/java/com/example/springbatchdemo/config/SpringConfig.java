package com.example.springbatchdemo.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringConfig {

    @Value("classpath:org/springframework/batch/core/schema-drop-mysql.sql")
    private Resource dropRepositoryTables;

    @Value("classpath:org/springframework/batch/core/schema-mysql.sql")
    private Resource dataRepositorySchema;



}
