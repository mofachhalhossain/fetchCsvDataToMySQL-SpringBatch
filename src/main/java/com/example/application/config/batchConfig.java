package com.example.application.config;
import com.example.application.model.Data_Init;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;



@Configuration
@EnableBatchProcessing
public class batchConfig {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    // itemReader Subclass
    @Bean
    public FlatFileItemReader<Data_Init> reader(){
        FlatFileItemReader<Data_Init> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("WHO-COVID-19-global-data.csv"));
        reader.setLineMapper(getLineMapper());
        reader.setLinesToSkip(1);
        return reader;
    }

    private LineMapper<Data_Init> getLineMapper() {
        // lineMapper Subclass
        DefaultLineMapper<Data_Init> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"Date_reported","Country_code","Country","WHO_region","New_cases","Cumulative_cases","New_deaths","Cumulative_deaths"});
        lineTokenizer.setIncludedFields(new int[]{0,1,2,3,4,5,6,7});

        BeanWrapperFieldSetMapper<Data_Init>  fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Data_Init.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public Data_Processor processor(){
        return new Data_Processor();
    }

    @Bean
    public JdbcBatchItemWriter<Data_Init> writer(){
        JdbcBatchItemWriter<Data_Init> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        // query
        writer.setSql("insert into data(Date_reported,Country_code,Country,WHO_region,New_cases,Cumulative_cases,New_deaths,Cumulative_deaths) values (:Date_reported,:Country_code,:Country,:WHO_region,:New_cases,:Cumulative_cases,:New_deaths,:Cumulative_deaths)");
        writer.setDataSource(this.dataSource);

        return writer;
    }

    @Bean
    public Job importDataJob(){

        return this.jobBuilderFactory.get("Data_Import_Job")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return
        this.stepBuilderFactory.get("step1")
                .<Data_Init , Data_Init>chunk(15)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}
