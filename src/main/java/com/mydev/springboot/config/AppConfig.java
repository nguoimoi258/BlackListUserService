package com.mydev.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(
	basePackages= { "com.mydev.springboot", "com.mydev.springboot.controller"}	
)
public class AppConfig {

	@Autowired
	private Environment env;
	
	@Bean(name = "Abcd1234", destroyMethod = "shutdown")
	@Primary
	public DataSource dataSource() {
		String username = env.getProperty("spring.datasource.username");
		String password = env.getProperty("spring.datasource.password");
		String url = env.getProperty("spring.datasource.url");
		String driverClassName = env.getProperty("spring.datasource.driver-class-name");
		
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setPoolName("Abcd1234_Pool");
		
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl(url);
		dataSource.setDriverClassName(driverClassName);
		dataSource.setConnectionTimeout(60000); // 60 s
		dataSource.setMaximumPoolSize(5);
		
		return dataSource;
	}
}