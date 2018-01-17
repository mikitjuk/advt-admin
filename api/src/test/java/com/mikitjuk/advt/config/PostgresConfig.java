package com.mikitjuk.advt.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
public class PostgresConfig {

	@Bean
	public

	@Bean
	public DataSource dataSource() throws IOException, SQLException {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
		driverManagerDataSource.setUrl(pc.getJdbcUrl());
		driverManagerDataSource.setUsername(pc.getUsername());
		driverManagerDataSource.setPassword(pc.getPassword());

		return driverManagerDataSource;
	}

}
