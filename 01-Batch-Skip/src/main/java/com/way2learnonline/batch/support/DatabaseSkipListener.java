package com.way2learnonline.batch.support;

import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseSkipListener {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@OnSkipInRead
	public void log(Throwable throwable) {
		if(throwable instanceof FlatFileParseException) {
			FlatFileParseException exception = (FlatFileParseException) throwable;
			jdbcTemplate.update(
				"insert into skipped_product " +
				"(line,line_number) values (?,?)",
				exception.getInput(),exception.getLineNumber()
			);
	}
	}

}
