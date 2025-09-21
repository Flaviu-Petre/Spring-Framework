package com.SpringDataJPA.SpringDataEnablingCacheUsingRedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringDataEnablingCacheUsingRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataEnablingCacheUsingRedisApplication.class, args);
	}

}
