package com.tomtom.ecommerce.product;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@SpringBootApplication
public class EcommerceProductApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(EcommerceProductApplication.class);
	public static void main(String[] args) {
		LOGGER.info("Bringing application up at  {}" , new Date(System.currentTimeMillis()));
		SpringApplication.run(EcommerceProductApplication.class, args);
		LOGGER.info("Application is application up at  {}" , new Date(System.currentTimeMillis()));
	
	}
	
	@Configuration
	public class SpringFoxConfig {                                    
	    @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()    
	          .apis(RequestHandlerSelectors.basePackage("com.tomtom.ecommerce"))
	          .paths(PathSelectors.any())                          
	          .build();                                           
	    }
	}

}
