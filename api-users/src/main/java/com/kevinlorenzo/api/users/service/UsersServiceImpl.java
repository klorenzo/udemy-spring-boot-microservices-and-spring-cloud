package com.kevinlorenzo.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kevinlorenzo.api.users.dto.UserDto;
import com.kevinlorenzo.api.users.entity.UserEntity;
import com.kevinlorenzo.api.users.feign.AlbumsFeignClient;
import com.kevinlorenzo.api.users.model.AlbumResponseModel;
import com.kevinlorenzo.api.users.repository.UsersRepository;

import feign.FeignException;

@Service
public class UsersServiceImpl implements UsersService {

	private AlbumsFeignClient albumsFeignClient;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private Environment env;
	private RestTemplate restTemplate;
	private UsersRepository usersRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public UsersServiceImpl(AlbumsFeignClient albumsFeignClient, Environment env,
			BCryptPasswordEncoder bCryptPasswordEncoder, RestTemplate restTemplate, UsersRepository usersRepository) {
		this.albumsFeignClient = albumsFeignClient;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.env = env;
		this.restTemplate = restTemplate;
		this.usersRepository = usersRepository;
	}

	public UserDto create(UserDto userDto) {
		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		usersRepository.save(userEntity);

		UserDto createdUser = modelMapper.map(userEntity, UserDto.class);

		return createdUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = usersRepository.findByEmail(username);

		if (userEntity == null) {
			throw new UsernameNotFoundException(username);
		}

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = usersRepository.findByEmail(email);

		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}

		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserDetailsById(String userId) {
		UserEntity userEntity = usersRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}

		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

		// Using RestTemplate

		/*
		 * String albumsUrl = String.format(env.getProperty("microservice.url.albums"),
		 * userId); ResponseEntity<List<AlbumResponseModel>> listAlbumsResponse =
		 * restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new
		 * ParameterizedTypeReference<List<AlbumResponseModel>>() { });
		 * List<AlbumResponseModel> albums = listAlbumsResponse.getBody();
		 */

		// Using Feign Client

		List<AlbumResponseModel> albums = new ArrayList<>();

		try {
			albumsFeignClient.getAlbumsByUserId(userId);
		} catch (FeignException fe) {
			logger.error(fe.getMessage());
		}

		userDto.setAlbums(albums);

		return userDto;
	}

}
