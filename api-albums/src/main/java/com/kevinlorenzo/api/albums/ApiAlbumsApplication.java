package com.kevinlorenzo.api.albums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiAlbumsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAlbumsApplication.class, args);
	}

}
