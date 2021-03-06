package com.way2learnonline.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.way2learnonline.domain.Product;

public class ProductJdbcItemWriter implements ItemWriter<Product> {
	
	private static final String INSERT_PRODUCT = "insert into product (id,name,description,price) values(?,?,?,?)";
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;


	
	public void write(List<? extends Product> items) throws Exception {
		
		for(Product item : items) {
			System.out.println(item.getName());
			try {
				
						jdbcTemplate.update(
							INSERT_PRODUCT,
							item.getId(),item.getName(),item.getDescription(),item.getPrice()
						);	
										
			} catch (Exception e) {
				e.printStackTrace();
			}
						
		}
	}

}
