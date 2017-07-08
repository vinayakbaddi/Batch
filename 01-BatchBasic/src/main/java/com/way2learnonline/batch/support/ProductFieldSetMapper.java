package com.way2learnonline.batch.support;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.way2learnonline.domain.Product;

public class ProductFieldSetMapper implements FieldSetMapper<Product>{

	@Override
	public Product mapFieldSet(FieldSet fieldSet) throws BindException {

		Product product = new Product();
		product.setId(fieldSet.readString("ID"));
		product.setName(fieldSet.readString("NAM"));
		product.setPrice(fieldSet.readBigDecimal("PRICE"));
		product.setDescription(fieldSet.readString("DESCRIPTION"));
		
		return product;
	}

	

}
