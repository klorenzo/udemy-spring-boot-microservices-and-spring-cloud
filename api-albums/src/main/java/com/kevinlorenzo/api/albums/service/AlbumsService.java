package com.kevinlorenzo.api.albums.service;

import java.util.List;

import com.kevinlorenzo.api.albums.entity.AlbumEntity;

public interface AlbumsService {

	List<AlbumEntity> getAlbumsByUserId(String userId);

}
