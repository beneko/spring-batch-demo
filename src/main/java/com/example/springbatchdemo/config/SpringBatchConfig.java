package com.example.springbatchdemo.config;

import com.example.springbatchdemo.model.FlightTicket;
import com.example.springbatchdemo.service.CustomItemProcessor;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.net.MalformedURLException;

public class SpringBatchConfig {
    public SpringBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

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

    @Bean
    public ItemProcessor<FlightTicket, FlightTicket> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<FlightTicket> itemWriter(Marshaller marshaller) throws MalformedURLException {
        StaxEventItemWriter<FlightTicket> writer = new StaxEventItemWriter<>();
        writer.setMarshaller(marshaller);
        writer.setRootTagName("tickets");
        writer.setResource(outputResource);
        return writer;
    }

    @Bean
    public Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(FlightTicket.class);
        return marshaller;
    }
}
