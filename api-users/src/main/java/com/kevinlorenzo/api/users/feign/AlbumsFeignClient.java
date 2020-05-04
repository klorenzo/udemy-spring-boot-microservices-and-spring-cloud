package com.kevinlorenzo.api.users.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kevinlorenzo.api.users.model.AlbumResponseModel;

@FeignClient(name = "albums-microservice")
public interface AlbumsFeignClient {

	//@GetMapping("/users/{userId}/albums")
	//public List<AlbumResponseModel> getAlbumsByUserId(@PathVariable String userId);
	
	@GetMapping("/users/{userId}/albumss")
	public List<AlbumResponseModel> getAlbumsByUserId(@PathVariable String userId);

}
