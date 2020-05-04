package com.kevinlorenzo.api.albums.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevinlorenzo.api.albums.entity.AlbumEntity;
import com.kevinlorenzo.api.albums.model.AlbumResponseModel;
import com.kevinlorenzo.api.albums.service.AlbumsService;

@RestController
@RequestMapping("/users/{userId}/albums")
public class AlbumsController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AlbumsService albumsService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<AlbumResponseModel> getAlbumsByUserId(@PathVariable String userId) {

		List<AlbumResponseModel> listAlbumResponseModel = new ArrayList<>();

		List<AlbumEntity> listAlbumEntity = albumsService.getAlbumsByUserId(userId);

		if ((listAlbumEntity == null) || listAlbumEntity.isEmpty()) {
			return listAlbumResponseModel;
		}

		Type listType = new TypeToken<List<AlbumResponseModel>>() {
		}.getType();

		listAlbumResponseModel = new ModelMapper().map(listAlbumEntity, listType);

		logger.info("Returning " + listAlbumResponseModel.size() + " albums.");

		return listAlbumResponseModel;
	}

}
