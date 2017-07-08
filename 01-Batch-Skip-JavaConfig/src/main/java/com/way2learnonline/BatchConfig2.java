package com.way2learnonline;

import java.net.MalformedURLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.way2learnonline.batch.support.ProductFieldSetMapper;
import com.way2learnonline.domain.Product;

@Configuration
@EnableBatchProcessing  //creates job repository , job launcher , transaction manager and batch artifact factories
@Import(InfrastructureConfig.class)
public class BatchConfig2 {

	@Autowired
 	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
 	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
 	private DataSource dataSource;
	

	
	@Bean
	public Job job(Step step)
	{
		return jobBuilderFactory.get("UnZipAndreadWriteJob").start(step).build();
	
	}
	
	
	
	
	@Bean
	public Step step(ItemReader<Product> productReader,ItemWriter<Product> productWriter)
	{
		return stepBuilderFactory.get("readerWriter")
				.<Product,Product>chunk(3)  
				.reader(productReader)
				.writer(productWriter)
				.faultTolerant()   //we need to include the faultTolerant() call into the Step creation chain,
				.skip(FlatFileParseException.class) //so that we can configure skipLimit() and an exception to skip().
				.skipLimit(4)
				.build();
	}
	
	
	///////READER*****************************
	
	@Bean
	public FlatFileItemReader<Product> productFileReader() throws MalformedURLException
	{
		
		
		FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>();
		Resource resource = new ClassPathResource("Files/products.txt");
		reader.setResource(resource);
		
		//FieldSetMapper
		ProductFieldSetMapper fieldSetMapper = new ProductFieldSetMapper();
		
		//For line to tokens
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames(new String[]{"ID", "NAME", "DESCRIPTION", "PRICE"});
		
		//Creating linemapper and setting fieldset and tokenizer
		DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<Product>();
		lineMapper.setFieldSetMapper(fieldSetMapper);
		lineMapper.setLineTokenizer(lineTokenizer);
		
		//setting linemapper to reader
		reader.setLineMapper(lineMapper);
		
		//first line is heading
		reader.setLinesToSkip(1);
		
		return reader;
	}
	
	///////WRITER*****************************
	
	@Bean
	public ItemWriter<Product> productWriter()
	{
		JdbcBatchItemWriter<Product> jdbcBatchItemWriter = new JdbcBatchItemWriter<Product>();
		// bind item properties to SQL parameter names from JavaBeans properties.
		BeanPropertyItemSqlParameterSourceProvider<Product> bp = new BeanPropertyItemSqlParameterSourceProvider<Product>();
		jdbcBatchItemWriter.setItemSqlParameterSourceProvider(bp); // sql parameter values from named parameters 
		jdbcBatchItemWriter.setSql("INSERT INTO PRODUCT (ID, NAME, PRICE) VALUES(:id, :name, :price)");
		jdbcBatchItemWriter.setDataSource(dataSource);
		
		return jdbcBatchItemWriter;
	}
	
	
	
	
}
