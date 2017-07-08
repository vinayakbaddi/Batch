package com.way2learnonline.batch.support;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.validation.BindException;

import com.way2learnonline.domain.Product;

public class ProductRowMapper implements RowMapper<Product>{

	

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Product product = new Product();
		product.setId(rs.getString("id"));
		product.setName(rs.getString("name"));
		product.setPrice(rs.getBigDecimal("price"));
		product.setDescription(rs.getString("description"));
		
		return product;
		
	}

	

}
