package com.way2learnonline;

import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.way2learnonline.batch.ProductJdbcItemWriter;
import com.way2learnonline.batch.support.ProductFieldSetMapper;
import com.way2learnonline.domain.Product;

@Configuration
@EnableBatchProcessing  //creates job repository , job launcher , transaction manager and batch artifact factories
@Import(InfrastructureConfig.class)
public class BatchConfig {

	@Autowired
 	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
 	private StepBuilderFactory stepBuilderFactory;
	

	
	@Bean
	public Job job(Step step)
	{
		return jobBuilderFactory.get("readWriteProducts").flow(step).end().build();
	
	}
	
	
	
	
	@Bean
	public Step step(ItemReader<Product> productFileReader,ItemWriter<Product> productJdbcItemWriter)
	{
		return stepBuilderFactory.get("readerWriter")
				.<Product,Product>chunk(10)
				.reader(productFileReader)
				.writer(productJdbcItemWriter)
				.build();
	}
	
	
	@StepScope
	@Bean
	public FlatFileItemReader<Product> productFileReader(@Value("#{jobParameters[inputResource]}") String inputResource) throws MalformedURLException
	{
		FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>();
		Resource resource = new ClassPathResource(inputResource);
		reader.setResource(resource);
		
		
		ProductFieldSetMapper fieldSetMapper = new ProductFieldSetMapper();
		
		
		
		/*DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames(new String[]{"ID", "NAME", "DESCRIPTION", "PRICE"});*/
		
		FixedLengthTokenizer lineTokenizer = new FixedLengthTokenizer();
		lineTokenizer.setNames(new String[]{"ID", "NAME", "DESCRIPTION", "PRICE"});
		lineTokenizer.setColumns(new Range[]{new Range(1,9),new Range(10,35),new Range(36,50),new Range(51,56)});
		
		
		DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<Product>();
		lineMapper.setFieldSetMapper(fieldSetMapper);
		lineMapper.setLineTokenizer(lineTokenizer);
		
		reader.setLineMapper(lineMapper);
		
		
		
		return reader;
	}
	
	@Bean
	public ItemWriter productJdbcItemWriter()
	{
		return new ProductJdbcItemWriter();
	}
	
	
}
