package com.example.springbatchdemo.config;

import com.example.springbatchdemo.model.FlightTicket;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    @Value("read/entries.csv")
    private Resource inputResource;

    @Value("write/entries.xml")
    private Resource outputResource;

    @Bean
    public ItemReader<FlightTicket> itemReader() throws UnexpectedInputException, ParseException {
        FlatFileItemReader<FlightTicket> reader = new FlatFileItemReader<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        String[] tokens = {"name", "ticketnumber", "route", "ticketprice"};
        tokenizer.setNames(tokens);

        reader.setResource(inputResource);
        reader.setLinesToSkip(1);

        BeanWrapperFieldSetMapper<FlightTicket> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(FlightTicket.class);

        DefaultLineMapper<FlightTicket> defaultLineMapper = new DefaultLineMapper<>();

        defaultLineMapper.setLineTokenizer(tokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        reader.setLineMapper(defaultLineMapper);

        return reader;
    }
}
